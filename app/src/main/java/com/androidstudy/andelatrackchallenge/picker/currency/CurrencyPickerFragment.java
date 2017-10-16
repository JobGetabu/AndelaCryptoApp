package com.androidstudy.andelatrackchallenge.picker.currency;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.androidstudy.andelatrackchallenge.adapter.CurrencyAdapter;
import com.androidstudy.andelatrackchallenge.models.Country;
import com.androidstudy.andelatrackchallenge.utils.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by anonymous on 10/17/17.
 */

public class CurrencyPickerFragment extends DialogFragment implements OnItemClickListener<Country> {
    private static final String COUNTRIES = "COUNTRIES";

    private CurrencyAdapter adapter;
    private LinearLayoutManager layoutManager;
    private CurrencyPickerListener pickerListener;

    public static CurrencyPickerFragment newInstance(ArrayList<Country> countries) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(COUNTRIES, countries);

        CurrencyPickerFragment fragment = new CurrencyPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Country> countries = getArguments().getParcelableArrayList(COUNTRIES);
        // close dialog if country list is null
        if (countries == null) dismiss();

        if (getActivity() instanceof CurrencyPickerListener)
            pickerListener = (CurrencyPickerListener) getActivity();
        else
            dismiss();

        adapter = new CurrencyAdapter(countries, this);
        layoutManager = new LinearLayoutManager(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(getActivity())
                .title("Select currency")
                .adapter(adapter, layoutManager)
                .negativeText("Cancel")
                .build();
    }

    @Override
    public void onItemClick(Country country, int position) {
        pickerListener.onPicked(country, position);
        dismiss();
    }
}
