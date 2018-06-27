/*
 * BSD 3-Clause License
 *
 * Copyright 2018  Sage Bionetworks. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1.  Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2.  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * 3.  Neither the name of the copyright holder(s) nor the names of any contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission. No license is granted to the trademarks of
 * the copyright holders even if such marks are included in this software.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sagebionetworks.research.domain.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sagebionetworks.research.domain.step.ui.action.implementations.SkipToStepActionBase;
import org.sagebionetworks.research.domain.step.ui.action.interfaces.Action;

public class SkipToStepActionBaseTests extends IndividualActionTests {
    public static final SkipToStepActionBase COMPLETE = SkipToStepActionBase.builder().setButtonIconName("icon")
            .setButtonTitle("title").setSkipToStepIdentifier("skipTo").build();

    public static final SkipToStepActionBase NO_SKIP_TO = SkipToStepActionBase.builder().setButtonIconName("icon")
            .setButtonTitle("title").build();

    @Test
    public void testSkipToStepActionBase_Complete() {
        testCommon(COMPLETE, "SkipToStepActionBase_Complete.json");
    }

    @Test
    public void testSkipToStepActionBase_NoSkipTo() {
        testCommon(NO_SKIP_TO, "SkipToStepActionBase_NoSkipTo.json");
    }

    private void testCommon(Action expected, String filename) {
        Action action = this.readJsonFile(filename);
        assertNotNull(action);
        assertTrue(action instanceof SkipToStepActionBase);
        assertEquals(expected, action);
    }
}
