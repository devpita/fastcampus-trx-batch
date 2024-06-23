package com.pitachips.trxbatch.util;

import org.junit.jupiter.api.Test;

import com.pitachips.trxbatch.exceptions.TrxBatchInvalidArgumentException;

import static org.junit.jupiter.api.Assertions.*;

class MaskUtilUnitTest {

    @Test
    public void testMaskTailWithAsterisk() {
        assertThrows(TrxBatchInvalidArgumentException.class, () -> MaskUtil.maskTailWithAsterisk("1234567890", -1));

        assertNull(MaskUtil.maskTailWithAsterisk(null, 10));

        assertEquals("1234567890", MaskUtil.maskTailWithAsterisk("1234567890", 0));

        assertEquals("*****", MaskUtil.maskTailWithAsterisk("", 5));
        assertEquals("*****", MaskUtil.maskTailWithAsterisk("12", 5));

        assertEquals("1234******", MaskUtil.maskTailWithAsterisk("1234567890", 6));
        assertEquals("**********", MaskUtil.maskTailWithAsterisk("1234567890", 10));
    }

}
