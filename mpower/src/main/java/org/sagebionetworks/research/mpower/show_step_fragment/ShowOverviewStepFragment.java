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

package org.sagebionetworks.research.mpower.show_step_fragment;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.FloatProperty;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.sagebionetworks.research.domain.step.ui.action.interfaces.Action;
import org.sagebionetworks.research.mobile_ui.show_step.view.ShowStepFragmentBase;
import org.sagebionetworks.research.mobile_ui.show_step.view.ShowUIStepFragment;
import org.sagebionetworks.research.mobile_ui.show_step.view.ShowUIStepFragmentBase;
import org.sagebionetworks.research.mobile_ui.show_step.view.view_binding.UIStepViewBinding;
import org.sagebionetworks.research.mobile_ui.widget.ActionButton;
import org.sagebionetworks.research.mpower.R;
import org.sagebionetworks.research.mpower.step_binding.OverviewStepViewBinding;
import org.sagebionetworks.research.mpower.step_view.OverviewStepView;
import org.sagebionetworks.research.presentation.ActionType;
import org.sagebionetworks.research.presentation.model.interfaces.StepView;
import org.sagebionetworks.research.presentation.show_step.show_step_view_models.ShowUIStepViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShowOverviewStepFragment extends
        ShowUIStepFragmentBase<OverviewStepView, ShowUIStepViewModel<OverviewStepView>,
                OverviewStepViewBinding<OverviewStepView>> {
    @NonNull
    public static ShowOverviewStepFragment newInstance(@NonNull StepView stepView) {
        ShowOverviewStepFragment fragment = new ShowOverviewStepFragment();
        Bundle arguments = ShowStepFragmentBase.createArguments(stepView);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.mpower2_overview_step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View returnValue = super.onCreateView(inflater, container, savedInstanceState);
        // Hide all the extra information when the view is created.
        this.stepViewBinding.getScrollView().setScrollingEnabled(false);
        this.stepViewBinding.getTitle().setAlpha(0f);
        this.stepViewBinding.getText().setAlpha(0f);
        this.stepViewBinding.getOverallIconDescriptionLabel().setAlpha(0f);
        for (ImageView imageView : this.stepViewBinding.getIconImageViews()) {
            imageView.setAlpha(0f);
        }

        for (TextView iconLabel : this.stepViewBinding.getIconLabels()) {
            iconLabel.setAlpha(0f);
        }

        return returnValue;
    }

    @Override
    protected void handleActionButtonClick(@NonNull ActionButton actionButton) {
        @ActionType String actionType = this.getActionTypeFromActionButton(actionButton);
        if (ActionType.INFO.equals(actionType)) {
            this.scrollToBottomAndFadeIn();
        } else {
            this.showStepViewModel.handleAction(actionType);
        }
    }

    @NonNull
    @Override
    protected OverviewStepViewBinding<OverviewStepView> instantiateAndBindBinding(View view) {
        return new OverviewStepViewBinding<>(view);
    }

    protected void scrollToBottomAndFadeIn() {
        this.stepViewBinding.getScrollView().setScrollingEnabled(true);
        long duration = 300;
        List<ViewPropertyAnimator> fadeInAnimators = new ArrayList<>();
        fadeInAnimators.add(this.stepViewBinding.getTitle().animate().alpha(1f).setDuration(duration));
        fadeInAnimators.add(this.stepViewBinding.getText().animate().alpha(1f).setDuration(duration));
        fadeInAnimators.add(this.stepViewBinding.getOverallIconDescriptionLabel().animate().alpha(1f)
                .setDuration(duration));
        for (ImageView imageView : this.stepViewBinding.getIconImageViews()) {
            fadeInAnimators.add(imageView.animate().alpha(1f).setDuration(duration));
        }

        for (TextView iconLabel : this.stepViewBinding.getIconLabels()) {
            fadeInAnimators.add(iconLabel.animate().alpha(1f).setDuration(duration));
        }

        fadeInAnimators.add(this.stepViewBinding.getInfoButton().animate().alpha(0f).setDuration(duration));

        ScrollView scrollView = this.stepViewBinding.getScrollView();
        int bottomY = scrollView.getChildAt(0).getHeight();
        ObjectAnimator scrollViewAnimator = ObjectAnimator.ofInt(this.stepViewBinding.getScrollView(),
                "scrollY", bottomY).setDuration(duration);
        for (ViewPropertyAnimator animator : fadeInAnimators) {
            animator.start();
        }

        scrollViewAnimator.start();
    }
}
