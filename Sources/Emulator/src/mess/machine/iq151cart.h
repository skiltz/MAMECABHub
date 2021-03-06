/*********************************************************************

    iq151cart.h

    IQ151 cartridge slot pinout:

                     +--------+
        +12V         | 01  32 |   IO     /MW
        +12V         | 02  33 |   IO     /IOR
        +5V          | 03  34 |   IO     /IOW
        +5V          | 04  35 |    O     /NRDY
        GND          | 05  36 |    O     /HOLD
        GND          | 06  37 |   I      HLDA
        A0      IO   | 07  38 |    O     /RAM
        A1      IO   | 08  39 |    O     /INT0
        A2      IO   | 09  40 |    O     /INT1
        A3      IO   | 10  41 |    O     /INT2
        A4      IO   | 11  42 |    O     /INT3
        A5      IO   | 12  43 |    O     /INT4
        A6      IO   | 13  44 |    O     /VID
        A7      IO   | 14  45 |   I       OSC
        A8      IO   | 15  46 |   I       TTL
        A9      IO   | 16  47 |    O      NF
        A10     IO   | 17  48 |           N.C.
        A11     IO   | 18  49 |           N.C.
        A12     IO   | 19  50 |   I       /INIT
        A13     IO   | 20  51 |   IO      /SS
        A14     IO   | 21  52 |   IO      /SR
        A15     IO   | 22  53 |           N.C.
        D0      IO   | 23  54 |   IO      /ZS
        D1      IO   | 24  55 |   IO      /ZR
        D2      IO   | 25  56 |    O      /DMA
        D3      IO   | 26  57 |           GND
        D4      IO   | 27  58 |           GND
        D5      IO   | 28  59 |           -5V
        D6      IO   | 29  60 |           -5V
        D7      IO   | 30  61 |           -12V
        /MR     IO   | 31  62 |           -12V
                     +--------+

*********************************************************************/

#ifndef __IQ151CART_H__
#define __IQ151CART_H__

/***************************************************************************
    TYPE DEFINITIONS
***************************************************************************/

// ======================> iq151cart_interface

struct iq151cart_interface
{
	devcb_write_line                m_out_irq0_cb;
	devcb_write_line                m_out_irq1_cb;
	devcb_write_line                m_out_irq2_cb;
	devcb_write_line                m_out_irq3_cb;
	devcb_write_line                m_out_irq4_cb;
	devcb_write_line                m_out_drq_cb;
};


// ======================> device_iq151cart_interface

class device_iq151cart_interface : public device_slot_card_interface
{
public:
	// construction/destruction
	device_iq151cart_interface(const machine_config &mconfig, device_t &device);
	virtual ~device_iq151cart_interface();

	// reading and writing
	virtual void read(offs_t offset, UINT8 &data) { }
	virtual void write(offs_t offset, UINT8 data) { }
	virtual void io_read(offs_t offset, UINT8 &data) { }
	virtual void io_write(offs_t offset, UINT8 data) { }
	virtual UINT8* get_cart_base() { return NULL; }

	// video update
	virtual void video_update(bitmap_ind16 &bitmap, const rectangle &cliprect) { }
};

// ======================> iq151cart_slot_device

class iq151cart_slot_device : public device_t,
								public iq151cart_interface,
								public device_slot_interface,
								public device_image_interface
{
public:
	// construction/destruction
	iq151cart_slot_device(const machine_config &mconfig, const char *tag, device_t *owner, UINT32 clock);
	virtual ~iq151cart_slot_device();

	// device-level overrides
	virtual void device_start();
	virtual void device_config_complete();

	// image-level overrides
	virtual bool call_load();
	virtual bool call_softlist_load(char *swlist, char *swname, rom_entry *start_entry);

	virtual iodevice_t image_type() const { return IO_CARTSLOT; }
	virtual bool is_readable()  const { return 1; }
	virtual bool is_writeable() const { return 0; }
	virtual bool is_creatable() const { return 0; }
	virtual bool must_be_loaded() const { return 0; }
	virtual bool is_reset_on_load() const { return 1; }
	virtual const char *image_interface() const { return "iq151_cart"; }
	virtual const char *file_extensions() const { return "bin,rom"; }
	virtual const option_guide *create_option_guide() const { return NULL; }

	// slot interface overrides
	virtual const char * get_default_card_software(const machine_config &config, emu_options &options);

	// reading and writing
	virtual void read(offs_t offset, UINT8 &data);
	virtual void write(offs_t offset, UINT8 data);
	virtual void io_read(offs_t offset, UINT8 &data);
	virtual void io_write(offs_t offset, UINT8 data);
	virtual void video_update(bitmap_ind16 &bitmap, const rectangle &cliprect);

	devcb_resolved_write_line   m_out_irq0_func;
	devcb_resolved_write_line   m_out_irq1_func;
	devcb_resolved_write_line   m_out_irq2_func;
	devcb_resolved_write_line   m_out_irq3_func;
	devcb_resolved_write_line   m_out_irq4_func;
	devcb_resolved_write_line   m_out_drq_func;

	device_iq151cart_interface* m_cart;
};


// device type definition
extern const device_type IQ151CART_SLOT;


/***************************************************************************
    DEVICE CONFIGURATION MACROS
***************************************************************************/

#define MCFG_IQ151_CARTRIDGE_ADD(_tag,_config,_slot_intf,_def_slot) \
	MCFG_DEVICE_ADD(_tag, IQ151CART_SLOT, 0) \
	MCFG_DEVICE_CONFIG(_config) \
	MCFG_DEVICE_SLOT_INTERFACE(_slot_intf, _def_slot, false)

#endif /* __IQ151CART_H__ */
