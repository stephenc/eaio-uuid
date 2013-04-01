/*
 * Copyright 2012 Real Logic Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eaio.util.lang;

import java.util.concurrent.atomic.AtomicLong;

/**
 * see : https://github.com/mjpt777/examples/blob/master/src/java/uk/co/real_logic/queues/PaddedAtomicLong.java
 */
public class PaddedAtomicLong extends AtomicLong
{
    public PaddedAtomicLong()
    {
    }

    public PaddedAtomicLong(final long initialValue)
    {
        super(initialValue);
    }

    public volatile long p1, p2, p3, p4, p5, p6 = 7;

    /**
     * http://mechanical-sympathy.blogspot.co.uk/2011/08/false-sharing-java-7.html
     * @return
     */
    public long sumPaddingToPreventOptimisation()
    {
        return p1 + p2 + p3 + p4 + p5 + p6;
    }
}