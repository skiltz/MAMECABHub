
#include "emu.h"
#include "machine/pc_keyboards.h"
#include "machine/kb_keytro.h"
#include "machine/kb_msnat.h"
#include "machine/kb_pc83.h"
#include "machine/kb_pcxt83.h"
#include "machine/kb_pcat84.h"

SLOT_INTERFACE_START(pc_xt_keyboards)
	SLOT_INTERFACE(STR_KBD_KEYTRONIC_PC3270, PC_KBD_KEYTRONIC_PC3270)
	SLOT_INTERFACE(STR_KBD_IBM_PC_83, PC_KBD_IBM_PC_83)
	SLOT_INTERFACE(STR_KBD_IBM_PC_XT_83, PC_KBD_IBM_PC_XT_83)
SLOT_INTERFACE_END


SLOT_INTERFACE_START(pc_at_keyboards)
	SLOT_INTERFACE(STR_KBD_KEYTRONIC_PC3270, PC_KBD_KEYTRONIC_PC3270_AT)
	SLOT_INTERFACE(STR_KBD_MICROSOFT_NATURAL, PC_KBD_MICROSOFT_NATURAL)
	SLOT_INTERFACE(STR_KBD_IBM_PC_AT_84, PC_KBD_IBM_PC_AT_84)
	SLOT_INTERFACE(STR_KBD_IBM_3270PC_122, PC_KBD_IBM_3270PC_122)
SLOT_INTERFACE_END
