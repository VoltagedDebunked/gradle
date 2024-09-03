/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.api.plugins.quality;

import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.resources.TextResource;
import org.gradle.internal.instrumentation.api.annotations.NotToBeReplacedByLazyProperty;
import org.gradle.internal.instrumentation.api.annotations.ReplacesEagerProperty;

/**
 * Configuration options for the CodeNarc plugin.
 *
 * @see CodeNarcPlugin
 */
public abstract class CodeNarcExtension extends CodeQualityExtension {

    private final ProviderFactory providers;
    private final FileOperations fileOperations;

    public CodeNarcExtension(Project project) {
        super();
        providers = project.getProviders();
        fileOperations = ((ProjectInternal) project).getServices().get(FileOperations.class);
        getMaxPriority1Violations().convention(0);
        getMaxPriority2Violations().convention(0);
        getMaxPriority3Violations().convention(0);
    }

    /**
     * The CodeNarc configuration to use. Replaces the {@code configFile} property.
     *
     * @since 2.2
     */
    @NotToBeReplacedByLazyProperty(because = "TextResource has no lazy replacement")
    public TextResource getConfig() {
        return fileOperations.getResources().getText().fromFile(getConfigFile());
    }

    /**
     * The CodeNarc configuration to use. Replaces the {@code configFile} property.
     *
     * @since 2.2
     */
    public void setConfig(TextResource config) {
        getConfigFile().fileProvider(providers.provider(config::asFile));
    }

    /**
     * The CodeNarc configuration file to use.
     */
    @ReplacesEagerProperty
    public abstract RegularFileProperty getConfigFile();

    /**
     * The maximum number of priority 1 violations allowed before failing the build.
     */
    @ReplacesEagerProperty(originalType = int.class)
    public abstract Property<Integer> getMaxPriority1Violations();

    /**
     * The maximum number of priority 2 violations allowed before failing the build.
     */
    @ReplacesEagerProperty(originalType = int.class)
    public abstract Property<Integer> getMaxPriority2Violations();

    /**
     * The maximum number of priority 3 violations allowed before failing the build.
     */
    @ReplacesEagerProperty(originalType = int.class)
    public abstract Property<Integer> getMaxPriority3Violations();

    /**
     * The format type of the CodeNarc report. One of <code>html</code>, <code>xml</code>, <code>text</code>, <code>console</code>.
     */
    @ReplacesEagerProperty
    public abstract Property<String> getReportFormat();
}
