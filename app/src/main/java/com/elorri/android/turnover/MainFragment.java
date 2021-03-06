package com.elorri.android.turnover;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.MarkerView;
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
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
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

        mChart.setMarker(new TurnoverMarkerView(getContext(), R.layout.barview));

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
            aTotalTurnoverPipeline6.put("goal_turnover_ht", 200000d);
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
            //LineData lineData = generateLineData();
            combinedData.setData(barData);
            combinedData.setData(lineData);


            LinkedList<Highlight> highlights = new LinkedList<>();
            Integer count = barData.getEntryCount();
            if (count > 0) {
                barData.getDataSetByIndex(0).setHighlightEnabled(true);
                for (int i = 0; i < count; i++) {
                    Entry entry = barData.getDataSetByIndex(i).getEntryForIndex(0);
                    Highlight highlight = new Highlight(entry.getX(), i, 0);
                    highlight.setDataIndex(combinedData.getDataIndex(barData));
                    highlights.add(highlight);
                }
            }

            mChart.getXAxis().setAxisMaximum(combinedData.getXMax() + 0.5f);
            mChart.getXAxis().setLabelCount(combinedData.getEntryCount());


            mChart.clear();
            mChart.setData(combinedData);
            mChart.highlightValues(toArray(highlights));
            mChart.invalidate();
        }
    }

    private Highlight[] toArray(LinkedList<Highlight> highlights) {
        Highlight[] array = new Highlight[highlights.size()];
        for (int i = 0; i < highlights.size(); i++) {
            array[i] = highlights.get(i);
        }
        return array;
    }

    private LineData buildLine(List<YContentValues> barsContentValues) {

        LineData lineData = new LineData();
        lineData.setValueTextSize(8f);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                Log.e("Turnover", Thread.currentThread().getStackTrace()[2] + "");
                DecimalFormat format = new DecimalFormat("###,###,###,##0.00");
                return format.format(value) + "€";
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

            }
        });
        lineData.setHighlightEnabled(false);


        List<Entry> lineEntries = buildLinesEntries(barsContentValues);
        LineDataSet lineDataSet = buildLineDataSet(lineEntries);
        lineData.addDataSet(lineDataSet);
        return lineData;
    }

    private List<Entry> buildLinesEntries(List<YContentValues> barsContentValues) {
        ArrayList<Entry> entries = new ArrayList<>();
        int position = 0;
        for (YContentValues barContentValue : barsContentValues) {
            entries.add(new Entry(position, Float.valueOf(barContentValue.getAsString("goal_turnover_ht"))));
            position++;
        }
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


    /**
     * Genre d'adapter (le constructeur fait office de createView et le refreshContent de
     * bindView ) permet de customiser la vue qui est superposée à
     * chaque marker (cad barre).
     * Ici au lieu de mettre le montant de chaque total (turnover et pipeline), ce qui est mis
     * par défaut, on va mettre le total (turnover+pipeline) et la diff par rapport à l'année
     * précédente.
     */
    public static class TurnoverMarkerView extends MarkerView {

        private final TextView total_val;
        private final TextView percent_val;

        public TurnoverMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            total_val = (TextView) findViewById(R.id.total_val);
            percent_val = (TextView) findViewById(R.id.percent_val);
            Log.e("Turnover", Thread.currentThread().getStackTrace()[2] + "");
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            Log.e("Turnover", Thread.currentThread().getStackTrace()[2] + "");
            total_val.setText("Hello");
            percent_val.setText("it's me");
            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        }

    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 5; index++)
            entries.add(new Entry(index + 0.5f, getRandom(200, 10000)));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        //set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(0f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

}
