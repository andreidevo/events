package com.r.events.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.r.events.R;
import com.sackcentury.shinebuttonlib.ShineButton;

public class FilterBottomSheetDialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_filter, container, false);

        ShineButton liveCheckBtn = view.findViewById(R.id.live_check_btn);
        ShineButton peopleCheckBtn = view.findViewById(R.id.people_check_btn);
        ShineButton codeCheckBtn = view.findViewById(R.id.code_check_btn);
        ShineButton brushCheckBtn = view.findViewById(R.id.brush_check_btn);

        peopleCheckBtn.init(getActivity());
        liveCheckBtn.init(getActivity());
        codeCheckBtn.init(getActivity());
        brushCheckBtn.init(getActivity());

        peopleCheckBtn.setShapeResource(R.raw.heart);
        liveCheckBtn.setShapeResource(R.raw.like);
        codeCheckBtn.setShapeResource(R.raw.heart);
        brushCheckBtn.setShapeResource(R.raw.heart);

        return view;
    }
}
