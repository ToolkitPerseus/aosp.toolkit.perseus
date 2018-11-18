package com.earth.OsToolkit.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.earth.OsToolkit.R;
import com.earth.OsToolkit.ScriptActivity;

import java.io.*;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.earth.OsToolkit.Working.BaseClass.Checking.checkSupportQC3;

import static com.earth.OsToolkit.Working.BaseClass.BaseIndex.CHARGE_QC3;

public class ChargingFragment extends Fragment implements View.OnClickListener {

    LinearLayout linearLayout_qc3 ;
    SwitchCompat qc3_sw;
    TextView qc3_txt;

    Boolean result;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_charging,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        setWarning(view);
        setQC3(view);

        // QC 3.0
        linearLayout_qc3 = view.findViewById(R.id.qc3_linearlayout);
        linearLayout_qc3.setOnClickListener(this);
        /*
        qc3_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int result;
                if (isChecked) {
                    qc3_txt.setText(R.string.sw_en);
                    result = runScript.run(getActivity(),CHARGE_QC3);
                } else {
                    qc3_txt.setText(R.string.sw_dis);
                    result = runScript.run(getActivity(),CHARGE_QC3);
                }

                if (result == 1) {
                    Toast.makeText(getActivity(), R.string.toast_succeed, Toast.LENGTH_SHORT).show();
                } else {
                    qc3_sw.setChecked(false);
                    Toast.makeText(getActivity(), R.string.toast_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
        */
        qc3_sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(getActivity(),ScriptActivity.class)
                    .putExtra("script",CHARGE_QC3);
            startActivityForResult(intent,0);
            //startActivity(intent);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0 && resultCode == RESULT_CANCELED) {
            result = intent.getBooleanExtra("result", true);
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment,new ChargingFragment()).commit();

        Log.e("ChargingFragment","return");
    }

    public void setWarning(View view) {
        CardView cardView = view.findViewById(R.id.charging_cardview);
        if (!checkSupportQC3()) {
            cardView.setVisibility(View.GONE);
        }
    }

    public void setQC3(View view) {
        String result;
        try {
            Process process = Runtime.getRuntime().exec("cat /sys/class/power_supply/battery/allow_hvdcp3");
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            result = input.readLine();
            input.close();
        } catch (Exception e) {
            result = "0";
        }

        qc3_txt = view.findViewById(R.id.txt_qc3);
        qc3_sw = view.findViewById(R.id.switch_qc3);
        if (result != null) {
            if (result.equals("1")) {
                qc3_txt.setText(R.string.sw_en);
                qc3_sw.setChecked(true);
            } else {
                qc3_txt.setText(R.string.sw_dis);
                qc3_sw.setChecked(false);
            }
        } else {
            qc3_txt.setText(R.string.sw_dis);
            qc3_sw.setChecked(false);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qc3_linearlayout :
                RelativeLayout relativeLayout_qc3 = view.findViewById(R.id.rl_qc3);
                if (relativeLayout_qc3.getVisibility() == View.GONE) {
                    relativeLayout_qc3.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout_qc3.setVisibility(View.GONE);
                }
                break;
        }
    }

}