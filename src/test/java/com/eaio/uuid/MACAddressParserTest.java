/*
 * MACAddressParserTest.java
 * 
 * Created 30.01.2006.
 * 
 * eaio: UUID - an implementation of the UUID specification
 * Copyright (c) 2003-2008 Johann Burkard (jb@eaio.com) http://eaio.com.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.eaio.uuid;

import java.io.*;

import junit.framework.TestCase;

/**
 * Test case for the {@link com.eaio.uuid.MACAddressParser} class.
 *
 * @author <a href="mailto:jb@eaio.com">Johann Burkard</a>
 * @version $Id: MACAddressParserTest.java,v 1.4 2008/02/20 07:37:40 Johann Exp $
 */
public class MACAddressParserTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(MACAddressParserTest.class);
    }

    public void testParse() {
        assertNull(MACAddressParser.parse("kalifornia blorb blob"));
        assertNull(MACAddressParser.parse("bla: keks: blorb: blubbix:a"));
        assertEquals(
                "0:3:ba:1b:c4:74",
                MACAddressParser.parse("blablorb (10.20.30.40) at 0:3:ba:1b:c4:74 permanent published"));
        assertEquals(
                "00:12:F0:21:F1:57",
                MACAddressParser.parse("        Physikalische Adresse . . . . . . : 00-12-F0-21-F1-57"));
    }

    public void testParse2() {
        String addr;

        addr = "blablorb (10.20.30.40) at 0:3:ba:1b:c4:74 permanent published";
        for (int i = addr.length(); i > 40; --i) {
            assertEquals("0:3:ba:1b:c4:74",
                    MACAddressParser.parse(addr.substring(0, i)));
        }
        assertEquals("0:3:ba:1b:c4:7", MACAddressParser.parse(addr.substring(0,
                40)));
        for (int i = 39; i != 0; --i) {
            assertNull(MACAddressParser.parse(addr.substring(0, i)));
        }
    }

    public void testParseLanscan() {
        assertNull(MACAddressParser.parse("Hardware Station        Crd  Hdw   Net-Interface    NM   MAC       HP-DLPI DLPI"));
        assertNull(MACAddressParser.parse("Path     Address        In#  State NamePPA          ID   Type      Support Mjr#"));
        assertEquals(
                "001560045000",
                MACAddressParser.parse("0/1/2/0  0x001560045000 0    UP    lan0 snap0       1    ETHER       Yes   119"));
    }

    /**
     * <code>Description . . . . . . . . . . . : Broadcom 440x 10/100 Integrated Controller</code>
     * 
     * Mail from Graham Matthews, 2008-01-29.
     *
     */
    public void testBroadcom() {
        assertNull(MACAddressParser.parse("Description . . . . . . . . . . . : Broadcom 440x 10/100 Integrated Controller"));
        assertEquals(
                "00:1C:23:AD:D1:5A",
                MACAddressParser.parse("Physical Address. . . . . . . . . : 00-1C-23-AD-D1-5A"));
    }

    public void testMacOSXLeopard() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream("/macosx-leopard.txt");
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
        }
        finally {
            if (is != null) {
            try {
                is.close();
            }
            catch (IOException ex) {}
            }
        }
        byte[] buf = bos.toByteArray();

        String macosxLeopard = new String(buf, 0);
        String[] lines = macosxLeopard.split("\n");
        String mac = null;
        for (String line : lines) {
            mac = MACAddressParser.parse(line);
            if (mac != null) {
                break;
            }
        }
        assertEquals("00:1e:c2:00:43:51", mac);
    }

}
