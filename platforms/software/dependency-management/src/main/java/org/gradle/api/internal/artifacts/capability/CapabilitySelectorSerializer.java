/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.artifacts.capability;

import org.gradle.api.artifacts.capability.CapabilitySelector;
import org.gradle.api.artifacts.capability.ExactCapabilitySelector;
import org.gradle.api.artifacts.capability.FeatureCapabilitySelector;
import org.gradle.internal.serialize.Decoder;
import org.gradle.internal.serialize.Encoder;
import org.gradle.internal.serialize.Serializer;

import java.io.IOException;

public class CapabilitySelectorSerializer implements Serializer<CapabilitySelector> {

    private static final int EXACT_CAPABILITY_SELECTOR = 1;
    private static final int FEATURE_CAPABILITY_SELECTOR = 2;

    @Override
    public CapabilitySelector read(Decoder decoder) throws IOException {
        int type = decoder.readSmallInt();
        switch (type) {
            case EXACT_CAPABILITY_SELECTOR: return readExactCapabilitySelector(decoder);
            case FEATURE_CAPABILITY_SELECTOR: return readFeatureCapabilitySelector(decoder);
            default: throw new IllegalArgumentException("Unknown capability selector type: " + type);
        }
    }

    private static CapabilitySelector readExactCapabilitySelector(Decoder decoder) throws IOException {
        String group = decoder.readString();
        String name = decoder.readString();
        return new DefaultExactCapabilitySelector(group, name);
    }

    private static CapabilitySelector readFeatureCapabilitySelector(Decoder decoder) throws IOException {
        String feature = decoder.readString();
        return new DefaultFeatureCapabilitySelector(feature);
    }

    @Override
    public void write(Encoder encoder, CapabilitySelector value) throws IOException {
        if (value instanceof ExactCapabilitySelector) {
            encoder.writeSmallInt(EXACT_CAPABILITY_SELECTOR);
            writeExactCapabilitySelector(encoder, (ExactCapabilitySelector) value);
        } else if (value instanceof FeatureCapabilitySelector) {
            encoder.writeSmallInt(FEATURE_CAPABILITY_SELECTOR);
            writeFeatureCapabilitySelector(encoder, (FeatureCapabilitySelector) value);
        } else {
            throw new IllegalArgumentException("Unknown capability selector type: " + value.getClass());
        }
    }

    private static void writeExactCapabilitySelector(Encoder encoder, ExactCapabilitySelector value) throws IOException {
        encoder.writeString(value.getGroup());
        encoder.writeString(value.getName());
    }

    private static void writeFeatureCapabilitySelector(Encoder encoder, FeatureCapabilitySelector value) throws IOException {
        encoder.writeString(value.getFeatureName());
    }
}
