/**********************************************************************

    Amstrad PC1512 Keyboard emulation

    Copyright MESS Team.
    Visit http://mamedev.org for licensing and usage restrictions.

**********************************************************************/

#pragma once

#ifndef __PC1512_KEYBOARD__
#define __PC1512_KEYBOARD__


#include "emu.h"
#include "cpu/mcs48/mcs48.h"



//**************************************************************************
//  MACROS / CONSTANTS
//**************************************************************************

#define PC1512_KEYBOARD_TAG "pc1512_keyboard"



//**************************************************************************
//  INTERFACE CONFIGURATION MACROS
//**************************************************************************

#define MCFG_PC1512_KEYBOARD_ADD(_clock, _data) \
	MCFG_DEVICE_ADD(PC1512_KEYBOARD_TAG, PC1512_KEYBOARD, 0) \
	downcast<pc1512_keyboard_device *>(device)->set_clock_callback(DEVCB2_##_clock); \
	downcast<pc1512_keyboard_device *>(device)->set_data_callback(DEVCB2_##_data);



//**************************************************************************
//  TYPE DEFINITIONS
//**************************************************************************

// ======================> pc1512_keyboard_device

class pc1512_keyboard_device :  public device_t
{
public:
	// construction/destruction
	pc1512_keyboard_device(const machine_config &mconfig, const char *tag, device_t *owner, UINT32 clock);

	template<class _clock> void set_clock_callback(_clock clock) { m_write_clock.set_callback(clock); }
	template<class _data> void set_data_callback(_data data) { m_write_data.set_callback(data); }

	// optional information overrides
	virtual const rom_entry *device_rom_region() const;
	virtual machine_config_constructor device_mconfig_additions() const;
	virtual ioport_constructor device_input_ports() const;

	DECLARE_WRITE_LINE_MEMBER( data_w );
	DECLARE_WRITE_LINE_MEMBER( clock_w );
	DECLARE_WRITE_LINE_MEMBER( m1_w );
	DECLARE_WRITE_LINE_MEMBER( m2_w );

	DECLARE_READ8_MEMBER( kb_bus_r );
	DECLARE_WRITE8_MEMBER( kb_p1_w );
	DECLARE_READ8_MEMBER( kb_p2_r );
	DECLARE_WRITE8_MEMBER( kb_p2_w );
	DECLARE_READ8_MEMBER( kb_t0_r );
	DECLARE_READ8_MEMBER( kb_t1_r );

protected:
	// device-level overrides
	virtual void device_start();
	virtual void device_reset();
	virtual void device_timer(emu_timer &timer, device_timer_id id, int param, void *ptr);

private:
	enum
	{
		LED_CAPS = 0,
		LED_NUM
	};

	required_device<cpu_device> m_maincpu;
	required_ioport m_y1;
	required_ioport m_y2;
	required_ioport m_y3;
	required_ioport m_y4;
	required_ioport m_y5;
	required_ioport m_y6;
	required_ioport m_y7;
	required_ioport m_y8;
	required_ioport m_y9;
	required_ioport m_y10;
	required_ioport m_y11;
	required_ioport m_com;

	devcb2_write_line   m_write_clock;
	devcb2_write_line   m_write_data;

	int m_data_in;
	int m_clock_in;
	int m_kb_y;
	int m_joy_com;
	int m_m1;
	int m_m2;

	emu_timer *m_reset_timer;
};


// device type definition
extern const device_type PC1512_KEYBOARD;



#endif
