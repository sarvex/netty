/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.dns;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

public class AbstractDnsRecord implements DnsRecord {

    private final String name;
    private final DnsRecordType type;
    private final DnsRecordClass dnsClass;
    private final long timeToLive;

    protected AbstractDnsRecord(String name, DnsRecordType type, long timeToLive) {
        this(name, type, DnsRecordClass.IN, timeToLive);
    }

    protected AbstractDnsRecord(String name, DnsRecordType type, DnsRecordClass dnsClass, long timeToLive) {
        if (timeToLive < 0) {
            throw new IllegalArgumentException("timeToLive: " + timeToLive + " (expected: >= 0)");
        }

        this.name = ObjectUtil.checkNotNull(name, "name");
        this.type = ObjectUtil.checkNotNull(type, "type");
        this.dnsClass = ObjectUtil.checkNotNull(dnsClass, "dnsClass");
        this.timeToLive = timeToLive;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public DnsRecordType type() {
        return type;
    }

    @Override
    public DnsRecordClass dnsClass() {
        return dnsClass;
    }

    @Override
    public long timeToLive() {
        return timeToLive;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DnsRecord)) {
            return false;
        }

        final DnsRecord that = (DnsRecord) obj;
        return type().intValue() == that.type().intValue() &&
               dnsClass().intValue() == that.dnsClass().intValue() &&
               name().equals(that.name());
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31 +
               type().intValue() * 31 +
               dnsClass().intValue();
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder(64);
        buf.append(StringUtil.simpleClassName(this));
        buf.append('(');
        buf.append(name());
        buf.append(' ');
        buf.append(timeToLive());
        buf.append(' ');
        buf.append(dnsClass().name());
        buf.append(' ');
        buf.append(type().name());
        buf.append(')');
        return buf.toString();
    }
}
