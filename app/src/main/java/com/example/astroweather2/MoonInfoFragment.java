package com.example.astroweather2;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;

public class MoonInfoFragment extends Fragment {

    private MoonViewModel moonViewModel;

    private TextView moonriseTextView;
    private TextView moonsetTextView;
    private TextView newMoonTextView;
    private TextView fullMoonTextView;
    private TextView moonPhaseTextView;
    private TextView synodicDayTextView;

    public static MoonInfoFragment newInstance() {
        MoonInfoFragment fragment = new MoonInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moonViewModel = ViewModelProviders.of(getActivity()).get(MoonViewModel.class);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.moon_info_fragment, container,
                false);

        moonriseTextView = view.findViewById(R.id.moonriseText);
        moonsetTextView = view.findViewById(R.id.moonsetText);
        newMoonTextView = view.findViewById(R.id.newMoonText);
        fullMoonTextView = view.findViewById(R.id.fullMoonText);
        moonPhaseTextView = view.findViewById(R.id.moonPhaseText);
        synodicDayTextView = view.findViewById(R.id.synodicDayText);

        AstroCalculator.MoonInfo moonInfo = moonViewModel.getMoonInfo();
        if(moonInfo!=null){
            moonriseTextView.setText(moonInfo.getMoonrise().toString());
            moonsetTextView.setText(moonInfo.getMoonset().toString());
            newMoonTextView.setText(moonInfo.getNextNewMoon().toString());
            fullMoonTextView.setText(moonInfo.getNextFullMoon().toString());
            moonPhaseTextView.setText(String.valueOf(Math.round((moonInfo.getIllumination()*100))));
            Calendar today = Calendar.getInstance();
            AstroDateTime nextNewMoon = moonInfo.getNextNewMoon();
            Calendar newMoonCalendar = Calendar.getInstance();
            newMoonCalendar.set(nextNewMoon.getYear(), nextNewMoon.getMonth()-1, nextNewMoon.getDay(),today.get(Calendar.HOUR),today.get(Calendar.MINUTE), today.get(Calendar.SECOND));
            int synodicDay = (int)((newMoonCalendar.getTimeInMillis()-today.getTimeInMillis())/ (24 * 60 * 60 * 1000));
            if(synodicDay>0) synodicDay = 29-synodicDay;
            synodicDayTextView.setText(String.valueOf(synodicDay));
        }

        return view;

    }
}
