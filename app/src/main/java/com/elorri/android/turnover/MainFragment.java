package com.elorri.android.turnover;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.NaN;

/**
 * Created by Elorri on 17/03/2018.
 */

public class MainFragment extends Fragment {

    private CombinedChart mChart;
    private List<YContentValues> mBarsContentValues; //Bundle contenant toutes les données
    // nécessaires pour afficher le graph

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container);

        mChart = view.findViewById(R.id.barchart);
        //mChart.setScaleEnabled(false); //voir le switch case à quoi ça sert ?
        //mChart.setScaleXEnabled(true);
        //mChart.setScaleYEnabled(true);
        mChart.setTouchEnabled(true);//mChart.setTouchEnabled(

        Legend legend = mChart.getLegend();
        legend.setEnabled(true);
        if (legend.isEnabled()) {
            legend.setFormSize(20f);
            // legend.setEnabled(true);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setWordWrapEnabled(true);
            legend.setTextSize(15);
            legend.setYEntrySpace(5f);

            ArrayList<LegendEntry> legend_entries = new ArrayList<>();
            legend_entries.add(new LegendEntry("turnover_sup", Legend.LegendForm.DEFAULT, NaN,
                    NaN, null, getContext().getResources().getColor(android.R.color.holo_green_dark)));
            legend_entries.add(new LegendEntry("turnover_inf", Legend.LegendForm.DEFAULT, NaN,
                    NaN, null, getContext().getResources().getColor(android.R.color.holo_red_dark)));
            legend_entries.add(new LegendEntry("turnover_pipeline", Legend.LegendForm.DEFAULT, NaN,
                    NaN, null, getContext().getResources().getColor(android.R.color.holo_orange_dark)));
            legend.setCustom(legend_entries);
        }
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setNoDataText("Pas de données");
        mChart.getDescription().setEnabled(false);

        mChart.setDrawGridBackground(false); // Désactive le grille
        mChart.setExtraBottomOffset(20f);
        mChart.setHighlightPerDragEnabled(false);
        //mChart.setHighlightPerTapEnabled(false);


        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("Turnover", Thread.currentThread().getStackTrace()[2] + "");
            }

            @Override
            public void onNothingSelected() {
                Log.e("Turnover", Thread.currentThread().getStackTrace()[2] + "");
            }
        }); //Il faudra réactiver ça


        // X
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(13f);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false); // Disable vertical lines
        xAxis.setCenterAxisLabels(false);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float position, AxisBase axis) {
//                if (value >= charts_datas.size()) {
//                    return "";
//                } else {
//                    try {
//                        return ((PipelineDisplay.Data) (charts_datas.values()).toArray()[Math.round(value)]).xValue;
//                    } catch (Exception e) {
//                        return "";
//                    }
//                }
                String label = mBarsContentValues.get((int) position).getAsString("period");
                if (label != null) {
                    return label;
                }
                return "";
            }
        });

        // Y gauche
        YAxis left = mChart.getAxisLeft();
        left.setDrawGridLines(true);
        left.setDrawAxisLine(false);

        // Y droite
        mChart.getAxisRight().setEnabled(false);


        new TotalTurnOverAsyncTask().execute();

        return view;
    }

    private class TotalTurnOverAsyncTask extends AsyncTask<Bundle, Void, List<YContentValues>> {

        @Override
        protected List<YContentValues> doInBackground(Bundle... params) {

            List<YContentValues> totalTurnoverPipeline = new ArrayList<>();

            YContentValues aTotalTurnoverPipeline1 = new YContentValues();
            aTotalTurnoverPipeline1.put("period", "2017-01");
            aTotalTurnoverPipeline1.put("turnover_ht", 80000d);
            aTotalTurnoverPipeline1.put("turnover_pipeline_ht", 10000d);
            aTotalTurnoverPipeline1.put("goal_turnover_ht", 10000d);
            totalTurnoverPipeline.add(aTotalTurnoverPipeline1);

            YContentValues aTotalTurnoverPipeline2 = new YContentValues();
            aTotalTurnoverPipeline2.put("period", "2017-02");
            aTotalTurnoverPipeline2.put("turnover_ht", 100000d);
            aTotalTurnoverPipeline2.put("turnover_pipeline_ht", 10000d);
            aTotalTurnoverPipeline2.put("goal_turnover_ht", 11000d);
            totalTurnoverPipeline.add(aTotalTurnoverPipeline2);

            YContentValues aTotalTurnoverPipeline3 = new YContentValues();
            aTotalTurnoverPipeline3.put("period", "2017-03");
            aTotalTurnoverPipeline3.put("turnover_ht", 130000d);
            aTotalTurnoverPipeline3.put("turnover_pipeline_ht", 10000d);
            aTotalTurnoverPipeline3.put("goal_turnover_ht", 12000d);
            totalTurnoverPipeline.add(aTotalTurnoverPipeline3);


            YContentValues aTotalTurnoverPipeline4 = new YContentValues();
            aTotalTurnoverPipeline4.put("period", "2018-01");
            aTotalTurnoverPipeline4.put("turnover_ht", 90000d);
            aTotalTurnoverPipeline4.put("turnover_pipeline_ht", 10000d);
            aTotalTurnoverPipeline4.put("goal_turnover_ht", 13000d);
            totalTurnoverPipeline.add(aTotalTurnoverPipeline4);

            YContentValues aTotalTurnoverPipeline5 = new YContentValues();
            aTotalTurnoverPipeline5.put("period", "2018-02");
            aTotalTurnoverPipeline5.put("turnover_ht", 110000d);
            aTotalTurnoverPipeline5.put("turnover_pipeline_ht", 10000d);
            aTotalTurnoverPipeline5.put("goal_turnover_ht", 14000d);
            totalTurnoverPipeline.add(aTotalTurnoverPipeline5);

            YContentValues aTotalTurnoverPipeline6 = new YContentValues();
            aTotalTurnoverPipeline6.put("period", "2018-03");
            aTotalTurnoverPipeline6.put("turnover_ht", 110000d);
            aTotalTurnoverPipeline6.put("turnover_pipeline_ht", 10000d);
            aTotalTurnoverPipeline6.put("goal_turnover_ht", 15000d);
            totalTurnoverPipeline.add(aTotalTurnoverPipeline6);

            return totalTurnoverPipeline;
        }

        @Override
        protected void onPostExecute(List<YContentValues> barsContentValues) {

            mBarsContentValues = barsContentValues; //Besoin plus tard pour retrouver label

            //Le graph affiche à la fois des points reliés par une ligne (LineData) et des barres
            // (BarData), c'est donc un graph combiné (CombinedData)
            CombinedData combinedData = new CombinedData();
            BarData barData = buildBars(barsContentValues);
            LineData lineData = buildLine(barsContentValues);
            combinedData.setData(barData);
            combinedData.setData(lineData);


//            LinkedList<Highlight> hilights = new LinkedList<>();
//            Integer count = barData.getEntryCount();
//            Highlight[] highs = new Highlight[0];
//            if (count > 0) {
//                barData.getDataSetByIndex(0).setHighlightEnabled(true);
//                for (int i = 0; i < count; i++) {
//                    Entry entry = barData.getDataSetByIndex(0).getEntryForIndex(i);
//                    Highlight h = new Highlight(entry.getX(), 0, 3);
//                    //Highlight h = new Highlight(entry.getX(), entry.getY(), 5f, 5f, 0, 3, left.getAxisDependency());
//                    h.setDataIndex(combinedData.getDataIndex(barData));
//                    hilights.add(h);
//                }
//
//                highs = hilights.toArray(new Highlight[hilights.size()]);
//            }

            mChart.getXAxis().setAxisMaximum(combinedData.getXMax() + 0.5f);
            mChart.getXAxis().setLabelCount(combinedData.getEntryCount());


            mChart.clear();
            mChart.setData(combinedData);
//            mChart.highlightValues(highs);
        }
    }

    private LineData buildLine(List<YContentValues> barsContentValues) {

        LineData lineData = new LineData();
        lineData.setValueTextSize(8f);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//Appelé comme ça
                //(" €", 2, 1)

                //Dans constructeur
                //				this.mAppendix = appendix;
//				this.type = type;
//				StringBuilder b = new StringBuilder();
//				for (int i = 0; i < decimals; i++) {
//					if (i == 0)
//						b.append(".");
//					b.append("0");
//				}
//
//				this.mFormat = new DecimalFormat("###,###,###,##0" + b.toString());


//                if (type == 0) {
//                    if ((// (Dataentry.getData()).keyOfValue(value) == ((Data) entry.getData())
//                            .max_index) {
//                        return mFormat.format(entry.getY()) + mAppendix;
//                    }
//                } else if (value > 0f || value < 0f) {
//                    return mFormat.format(value) + mAppendix;
//                }
//                return "";
                Log.e("Turnover", Thread.currentThread().getStackTrace()[2] + "");
                return "";
            }
        });
        lineData.setHighlightEnabled(false);

        int position = 0;
        for (YContentValues barContentValue : barsContentValues) {
            List<Entry> lineEntries = buildLinesEntries(position, barContentValue);
            LineDataSet lineDataSet = buildLineDataSet(lineEntries);
            lineData.addDataSet(lineDataSet);
            position++;
        }
        return lineData;
    }

    private List<Entry> buildLinesEntries(int position, YContentValues barContentValue) {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(position, Float.valueOf(barContentValue.getAsString("goal_turnover_ht"))));
        return entries;
    }

    private LineDataSet buildLineDataSet(List<Entry> lineEntries) {
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "");
        //lineDataSet.setColor(ToolsRes.getColor(R.color.black));
        lineDataSet.setColor(getContext().getResources().getColor(android.R.color.black));
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.disableDashedHighlightLine();
        //lineDataSet.setCircleColor(ToolsRes.getColor(R.color.txt_color));
        lineDataSet.setCircleColor(getContext().getResources().getColor(android.R.color.black));
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setLineWidth(0f);
        return lineDataSet;
    }

    private BarData buildBars(List<YContentValues> barsContentValues) {
        // Creation des barres. Chaque barre peut avoir plusieurs niveaux (exple le pipeline se
        // trouve au dessus du turnover). Les données communes à toutes les barres sont
        // définies dans BarData, une barre est representée par un DataSet, chaque niveau de
        // la barre est défini dans une BarEntry.

        BarData barData = new BarData();
        barData.setValueTextSize(13f);
        barData.setBarWidth(0.7f);
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                Log.e("Turnover", Thread.currentThread().getStackTrace()[2] + "");
                return "";
            }
        });

        int position = 0;
        for (YContentValues barContentValue : barsContentValues) {
            List<BarEntry> barEntries = buildBarEntries(position, barContentValue);
            //Creation de la liste de BarEntry
            BarDataSet dataSet = buildDataSet(barEntries, "" /*barContentValue.getAsString("period")*/);//Creation du DataSet
            barData.addDataSet(dataSet); //Ajout de la barre à l'ensemble des barres
            position++;
        }
        return barData;
    }

    private List<BarEntry> buildBarEntries(int position, YContentValues barContentValue) {
        //Il ne peut y avoir que 2 niveaux sur la barre : turnover et pipeline, donc 2 barreEntry
        // max.
        List<BarEntry> barEntries = new ArrayList<>();

        //Turnover : le turnover est toujours présent
        float[] value = new float[]{
                Float.valueOf(barContentValue.getAsString("turnover_ht")),
                Float.valueOf(barContentValue.getAsString("turnover_pipeline_ht"))
        };
        BarEntry barEntry = new BarEntry(position, value);
//        barEntry.setData("hello");
//        barEntry.getSumBelow(1);
        barEntries.add(barEntry);

        return barEntries;
    }

    private BarDataSet buildDataSet(List<BarEntry> barEntries, String label) {
        BarDataSet dataSet = new BarDataSet(barEntries, label) {
            @Override
            public int getColor(int index) {
                if (index == 0) { //Ajouter le test sur show_obj
                    return getContext().getResources().getColor(android.R.color
                            .holo_green_dark);
                }
                return getContext().getResources().getColor(android.R.color
                        .holo_orange_dark);
            }
        };
        dataSet.setDrawValues(dataSet.isDrawValuesEnabled());
        //Les couleurs sont réutilisées dans l'ordre à chaque nouvelle BarEntry faut-il
        // vraiment en mettre 3 ? Oui le fait d'avoir getColor n'est pas suffisant pour qu'il
        // détermine la couleur apparement
        dataSet.setColors(getContext().getResources().getColor(android.R.color.holo_green_dark),
                getContext().getResources().getColor(android.R.color.holo_orange_dark));
        dataSet.setHighLightAlpha(0); //Est-ce utile ? essayer avec 255 pour voir
        return dataSet;
    }
}
