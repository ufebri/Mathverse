package com.raylabs.mathverse.feature.blackwhite.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raylabs.mathverse.feature.blackwhite.domain.BuildBwLabelsUseCase;
import com.raylabs.mathverse.feature.blackwhite.domain.BwLabels;

public class BwViewModel extends ViewModel {
    private final BuildBwLabelsUseCase buildLabels;
    private final MutableLiveData<BwLabels> state = new MutableLiveData<>();

    public BwViewModel() {
        this(new BuildBwLabelsUseCase());
    }

    public BwViewModel(BuildBwLabelsUseCase useCase) {
        this.buildLabels = useCase;
    }

    public LiveData<BwLabels> getState() {
        return state;
    }

    public void setShowingColor(boolean isShowingColor, String whiteLabel, String blackLabel) {
        BwLabels m = buildLabels.invoke(isShowingColor, whiteLabel, blackLabel);
        state.setValue(m);
    }
}