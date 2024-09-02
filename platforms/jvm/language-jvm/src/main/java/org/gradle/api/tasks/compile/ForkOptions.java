/*
 * Copyright 2007 the original author or authors.
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

package org.gradle.api.tasks.compile;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.internal.instrumentation.api.annotations.ReplacesEagerProperty;

/**
 * Fork options for Java compilation. Only take effect if {@code CompileOptions.fork} is {@code true}.
 */
public abstract class ForkOptions extends ProviderAwareCompilerDaemonForkOptions {
    private static final long serialVersionUID = 0;

    /**
     * Returns the compiler executable to be used.
     * <p>
     * Only takes effect if {@code CompileOptions.fork} is {@code true}. Not present by default.
     * <p>
     * Setting the executable disables task output caching.
     */
    @Optional
    @Input
    @ReplacesEagerProperty
    public abstract Property<String> getExecutable();

    /**
     * Returns the Java home which contains the compiler to use.
     * <p>
     * Only takes effect if {@code CompileOptions.fork} is {@code true}. Not present by default.
     *
     * @since 3.5
     */
    @Internal
    @ReplacesEagerProperty
    public abstract DirectoryProperty getJavaHome();

    /**
     * Returns the directory used for temporary files that may be created to pass
     * command line arguments to the compiler process. Not present by default,
     * in which case the directory will be chosen automatically.
     */
    @Internal
    @ReplacesEagerProperty
    public abstract Property<String> getTempDir();
}
