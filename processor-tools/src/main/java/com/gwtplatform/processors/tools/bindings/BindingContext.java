/*
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.processors.tools.bindings;

import com.google.common.base.Optional;
import com.gwtplatform.processors.tools.domain.Type;

import static com.google.common.base.Optional.of;

public class BindingContext {
    private final Type moduleType;
    private final Optional<Type> implementation;
    private final Optional<Type> parent;
    private final Optional<Type> annotation;
    private final Optional<Type> scope;
    private final boolean subModule;
    private final boolean setBinder;

    private boolean eagerSingleton;
    private boolean outputNow;

    BindingContext(
            Type moduleType,
            Optional<Type> implementation,
            Optional<Type> parent,
            Optional<Type> annotation,
            Optional<Type> scope,
            boolean subModule,
            boolean setBinder) {
        this.moduleType = moduleType;
        this.implementation = implementation;
        this.parent = parent;
        this.annotation = annotation;
        this.scope = scope;
        this.subModule = subModule;
        this.setBinder = setBinder;
    }

    public static BindingContext newModule(Type module) {
        return new BindingContext(module, absentType(), absentType(), absentType(), absentType(), true, false);
    }

    public static BindingContext flushModule(Type module) {
        BindingContext context = newModule(module);
        context.outputNow = true;
        return context;
    }

    public static BindingContext newSubModule(
            Type module,
            Type subModule) {
        return new BindingContext(module, of(subModule), absentType(), absentType(), absentType(), true, false);
    }

    public static BindingContext newSetBinder(
            Type module,
            Type parent,
            Type implementation) {
        return new BindingContext(module, of(implementation), of(parent), absentType(), absentType(), false, true);
    }

    public static BindingContext newSetBinder(
            Type module,
            Type parent,
            Type implementation,
            Class<?> scope) {
        return new BindingContext(module, of(implementation), of(parent), absentType(), of(new Type(scope)), false,
                true);
    }

    public static BindingContext newBinding(
            Type module,
            Type implementation) {
        return new BindingContext(module, of(implementation), absentType(), absentType(), absentType(), false, false);
    }

    public static BindingContext newBinding(
            Type module,
            Type implementation,
            Class<?> scope) {
        return new BindingContext(module, of(implementation), absentType(), absentType(), of(new Type(scope)), false,
                false);
    }

    public static BindingContext newBinding(
            Type module,
            Type parent,
            Type implementation) {
        return new BindingContext(module, of(implementation), of(parent), absentType(), absentType(), false, false);
    }

    public static BindingContext newBinding(
            Type module,
            Type parent,
            Type implementation,
            Class<?> scope) {
        return new BindingContext(module, of(implementation), of(parent), absentType(), of(new Type(scope)), false,
                false);
    }

    public static BindingContext newAnnotatedBinding(
            Type module,
            Type parent,
            Type implementation,
            Class<?> annotation,
            Class<?> scope) {
        return new BindingContext(module, of(implementation), of(parent), of(new Type(annotation)), of(new Type(scope)),
                false, false);
    }

    public static BindingContext newEagerSingletonBinding(
            Type module,
            Type parent,
            Type implementation) {
        BindingContext bindingContext = newBinding(module, parent, implementation);
        bindingContext.eagerSingleton = true;
        return bindingContext;
    }

    private static Optional<Type> absentType() {
        return Optional.absent();
    }

    public Type getModuleType() {
        return moduleType;
    }

    public Optional<Type> getImplementation() {
        return implementation;
    }

    public Optional<Type> getParent() {
        return parent;
    }

    public Optional<Type> getScope() {
        return scope;
    }

    public boolean isEagerSingleton() {
        return eagerSingleton;
    }

    public boolean isSubModule() {
        return subModule;
    }

    public boolean isSetBinder() {
        return setBinder;
    }

    public boolean isOutputNow() {
        return outputNow;
    }
}
