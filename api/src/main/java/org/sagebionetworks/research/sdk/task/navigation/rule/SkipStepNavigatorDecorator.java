/*
 *    Copyright 2017 Sage Bionetworks
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package org.sagebionetworks.research.sdk.task.navigation.rule;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import org.sagebionetworks.research.sdk.result.TaskResult;
import org.sagebionetworks.research.sdk.step.Step;
import org.sagebionetworks.research.sdk.task.navigation.StepNavigatorDecorator;
import org.sagebionetworks.research.sdk.task.navigation.rule.factory.NavigationRuleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by liujoshua on 10/6/2017.
 */

public class SkipStepNavigatorDecorator extends StepNavigatorDecorator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkipStepNavigatorDecorator.class);

    private final NavigationRuleFactory<NavigationRule.Skip> skipRuleFactory;

    public SkipStepNavigatorDecorator(@NonNull StepNavigator navigatorToBeDecorated,
                                      @NonNull NavigationRuleFactory<NavigationRule.Skip>
                                              skipRuleFactory) {
        super(navigatorToBeDecorated);
        checkNotNull(skipRuleFactory);

        this.skipRuleFactory = skipRuleFactory;
    }

    @Nullable
    @Override
    public Step getNextStep(Step step, @Nullable TaskResult taskResult) {
        Step nextStep;
        do {
            nextStep = super.getNextStep(step, taskResult);
            if (nextStep == null) {
                return null;
            }
            LOGGER.debug("Check skip rule for step: " + step);
        } while (shouldSkip(nextStep, taskResult));

        return nextStep;
    }

    @VisibleForTesting
    boolean shouldSkip(@NonNull Step step, @Nullable TaskResult taskResult) {
        NavigationRule.Skip skipRule = skipRuleFactory.create(step);
        if (skipRule == null) {
            return false;
        }

        boolean shouldSkip = skipRule.shouldSkip(step, taskResult);
        LOGGER.debug("Applying skip rule: " + skipRule + ", shouldSkip: " + shouldSkip);

        return shouldSkip;
    }

}
