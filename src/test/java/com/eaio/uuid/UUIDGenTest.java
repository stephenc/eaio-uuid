/*
 * UUIDGenTest.java
 * 
 * Created 21.02.2006.
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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.TestCase;

/**
 * Test case for the {@link UUIDGenTest} class.
 *
 * @author <a href="mailto:jb@eaio.com">Johann Burkard</a>
 * @version $Id: UUIDGenTest.java,v 1.3 2008/02/20 07:37:41 Johann Exp $
 */
public class UUIDGenTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(UUIDGenTest.class);
    }

    /*
     * Test method for 'com.eaio.uuid.UUIDGen.getFirstLineOfCommand(String)'
     */
    public void testGetFirstLineOfCommand() {
        String osName = System.getProperty("os.name", "");
        if (!osName.startsWith("Windows")) return;

        try {
            if (!InetAddress.getLocalHost().getHostName().equals("n0t3b00k")) return;
        }
        catch (UnknownHostException ex) {}

        // This only works on my computer

        try {
            assertTrue(UUIDGen.getFirstLineOfCommand(
                    new String[] { "cmd", "/c", "dir" }).indexOf(
                    "in Laufwerk C: ist") != -1);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

    }

}
