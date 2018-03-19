package com.elorri.android.turnover;

public class PipelineDisplay{}

/**
 * cette classe permet l'affichage d'un graph de pipeline commercial
 */
//public class PipelineDisplay implements OnChartValueSelectedListener {
//
//	private static int green = ToolsRes.getColor(R.color.btn_submit_default);
//	private static int orange = ToolsRes.getColor(R.color.secondary);
//	private static int yellow_dark = Color.rgb(220, 220, 0);
//
//	private final int mobile;
//	private final int land_mobile;
//	private final int tablet;
//	private final int land_tablet;
//
//	//Views
//	private final View min_height_progress_view; //progess related
//	private final View circular_progress; //progess related
//	private final ViewGroup graph_container;
//	private final View _layout;
//	private CustomCombinedChart mChart;
//
//
//	private YAxis left;
//	private XAxis xAxis;
//	private StatsPipelineAsyncTask statsPipelineAsyncTask;
//
//
//	private LinkedHashMap<String, Data> charts_datas;
//	public Date date_start;
//	public Date date_end;
//
//	public int choice_id; //Choix 'Ca commandé' 'Ca facturé' 'Marge commandé' 'Marge facturée' ...
//	public ArrayList<Integer> subchoice_ids; //Si le choice_id est 'Ca commandé' ou 'Marge commandé' il nous faut le subchoice_id pour générer le graph.
//
//	public boolean show_obj = true;
//
//	private Integer usr_id = 0;
//	public int seller_id = 0;
//
//	private PipelineDisplayInterface mInterface;
//	public Integer period_display = PeriodDisplayFilterView.MONTH; // Uniquement utilisé pour l'affichage et donc le changement de groupe by dans les reqûete
//	private Highlight[] highs;
//	private Legend legend;
//
//	private StatsPipelineAsyncTask.StatsPipelineAsyncTaskInterface taskInterface;
//	private List<YContentValues> _pipeline_turnover;
//
//	/**
//	 * @param arguments  - seller_id ( Integer )
//	 *                   - period (String) { all | year | week | day | month }
//	 *                   - date_start ( Date )
//	 *                   - date_end ( Date )
//	 *                   - setScaleEnabled ( BOOL )
//	 *                   -1 = non scallable
//	 *                   0 = scallable sur x
//	 *                   1 = scallable sur y
//	 *                   2 = scallable sur x et y
//	 *                   - setTouchEnabled ( BOOL ) default "false"
//	 *                   - setLegendEnabled ( BOOL ) default "false"
//	 * @param mInterface Interface
//	 */
//	public PipelineDisplay(Bundle arguments, final PipelineDisplayInterface mInterface) {
//		// View Wrapper
//		this.mInterface = mInterface;
//
//		_layout = Const.main_activity.getLayoutInflater().inflate(R.layout.stats_pipeline_display, null);
//
//		//Progress
//		min_height_progress_view = _layout.findViewById(R.id.min_height_progress_view);
//		circular_progress = _layout.findViewById(R.id.circular_progress);
//		graph_container = (ViewGroup) _layout.findViewById(R.id.pipeline_display_content);
//		turnOnProgress(true);
//
//		//Graph
//		mChart = new CustomCombinedChart(MainApplication.getAppContext());
//		MyMarkerView mv = new MyMarkerView(Const.main_activity, R.layout.graph_marker_view);
//		mChart.setMarker(mv);
//		mChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Tools.dip(600)));
//		graph_container.addView(mChart);
//		legend = mChart.getLegend();
//
//
//		period_display = arguments.getInt("period_display", PeriodDisplayFilterView.MONTH);
//		choice_id = arguments.getInt("choice_id");
//		usr_id = arguments.getInt("usr_id");
//		show_obj = arguments.getBoolean("show_obj", true);
//		date_start = (Date) arguments.getSerializable("date_start");
//		date_end = (Date) arguments.getSerializable("date_end");
//		seller_id = arguments.getInt("seller_id");
//
//
//
//		// region settings
//		mChart.setScaleEnabled(false);
//		if (arguments.containsKey("setScaleEnabled")) {
//			if (arguments.getInt("setScaleEnabled") == -1) {
//				mChart.setScaleEnabled(false);
//			} else if (arguments.getInt("setScaleEnabled") == 0) {
//				mChart.setScaleXEnabled(true);
//			} else if (arguments.getInt("setScaleEnabled") == 1) {
//				mChart.setScaleYEnabled(true);
//			} else if (arguments.getInt("setScaleEnabled") == 2) {
//				mChart.setScaleEnabled(true);
//			}
//		}
//
//		mobile = arguments.getInt("mobile");
//		land_mobile = arguments.getInt("land_mobile");
//		tablet = arguments.getInt("tablet");
//		land_tablet = arguments.getInt("land_tablet");
//
//		//invalidate();
//
//		//mChart.setMaxVisibleValueCount(arguments.getInt("setMaxVisibleValueCount", 12));
//		mChart.setTouchEnabled(arguments.getBoolean("setTouchEnabled", false));
//		// endregion
//
//		legend.setEnabled(arguments.getBoolean("setLegendEnabled", false));
//		if (legend.isEnabled()) {
//			legend.setFormSize(20f);
//			legend.setEnabled(true);
//			legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//			legend.setWordWrapEnabled(true);
//			legend.setTextSize(15);
//			legend.setYEntrySpace(5f);
//
//			ArrayList<LegendEntry> legend_entries = new ArrayList<>();
//
//			if (show_obj) {
//				legend_entries.add(new LegendEntry(ToolsRes.getString(R.string.turnover_sup), Legend.LegendForm.DEFAULT, NaN, NaN, null, green));
//				legend_entries.add(new LegendEntry(ToolsRes.getString(R.string.turnover_inf), Legend.LegendForm.DEFAULT, NaN, NaN, null, orange));
//			} else {
//				legend_entries.add(new LegendEntry(ToolsRes.getString(R.string.turnover), Legend.LegendForm.DEFAULT, NaN, NaN, null, green));
//			}
//			legend_entries.add(new LegendEntry(ToolsRes.getString(R.string.turnover_secured), Legend.LegendForm.DEFAULT, NaN, NaN, null, yellow_dark));
//
//			legend.setCustom(legend_entries);
//		}
//
//		mChart.setBackgroundColor(Color.WHITE);
//		mChart.setNoDataText(ToolsRes.getString(R.string.no_data_available));
//		mChart.getDescription().setEnabled(false);
//
//		mChart.setDrawGridBackground(false); // Désactive le grille
//		mChart.setExtraBottomOffset(20f);
//		mChart.setHighlightPerDragEnabled(false);
//		//mChart.setHighlightPerTapEnabled(false);
//
//
//		mChart.setOnChartValueSelectedListener(this);
//
//
//		// X
//		xAxis = mChart.getXAxis();
//		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//		xAxis.setTextSize(13f);
//		xAxis.setDrawAxisLine(false);
//		xAxis.setGranularity(1f);
//		xAxis.setDrawGridLines(false); // Disable vertical lines
//		xAxis.setCenterAxisLabels(false);
//		xAxis.setAxisMinimum(-0.5f);
//		xAxis.setValueFormatter(new IAxisValueFormatter() {
//			@Override
//			public String getFormattedValue(float value, AxisBase axis) {
//				if (value >= charts_datas.size()) {
//					return "";
//				} else {
//					try {
//						return ((Data) (charts_datas.values()).toArray()[Math.round(value)]).xValue;
//					} catch (Exception e) {
//						return "";
//					}
//				}
//			}
//		});
//
//		// Y gauche
//		left = mChart.getAxisLeft();
//		left.setDrawGridLines(true);
//		left.setDrawAxisLine(false);
//
//		// Y droite
//		mChart.getAxisRight().setEnabled(false);
//
//
//		//Charge les données pour le graph sur le background  et les affiche ensuite
//		Bundle bundle = new Bundle();
//		bundle.putInt("seller_id", seller_id); //Résultat du filtre seller
//		bundle.putInt("usr_id", usr_id); //??
//		bundle.putString("date_start", date_start != null ? Strings.convertDateJavaToEN(date_start) : null); //Résultat filtre 'Toute la periode'
//		bundle.putString("date_end", date_end != null ? Strings.convertDateJavaToEN(date_end) : null); //Résultat filtre 'Toute la periode'
//		bundle.putInt("order_margin_choice", choice_id); //Résultat filtre 'Chiffre d'affaire commandé'
//		bundle.putIntegerArrayList("order_states", subchoice_ids); //Résultat filtre 'Etat de commande'
//		bundle.putInt("period_type", period_display); //Résultat filtre 'Jour, Semaine, Mois, etc'
//		new TotalTurnOverAsyncTask().execute(bundle);
//
//
//
////		taskInterface = new StatsPipelineAsyncTask.StatsPipelineAsyncTaskInterface() {
////			@Override
////			public void onTaskFinish(LinkedHashMap<String, Data> task_charts_datas) {
////				charts_datas = task_charts_datas;
////
////				CombinedData combined_data = new CombinedData();
////
////				BarData be = generateBarData(charts_datas);
////				combined_data.setData(be);
////
////				if (period_display == PeriodDisplayFilterView.MONTH) {
////					combined_data.setData(generateLineData(charts_datas));
////				}
////
////				LinkedList<Highlight> hilights = new LinkedList<>();
////				Integer count = be.getEntryCount();
////				if (count > 0) {
////					be.getDataSetByIndex(0).setHighlightEnabled(true);
////					for (int i = 0; i < count; i++) {
////						Entry entry = be.getDataSetByIndex(0).getEntryForIndex(i);
////						Highlight h = new Highlight(entry.getX(), 0, 3);
////						//Highlight h = new Highlight(entry.getX(), entry.getY(), 5f, 5f, 0, 3, left.getAxisDependency());
////						h.setDataIndex(combined_data.getDataIndex(be));
////						hilights.add(h);
////					}
////
////					highs = hilights.toArray(new Highlight[hilights.size()]);
////				}
////
////				xAxis.setAxisMaximum(combined_data.getXMax() + 0.5f);
////				xAxis.setLabelCount(combined_data.getEntryCount());
////
////				mChart.clear();
////				if (combined_data.getEntryCount() > 0) {
////					mChart.setData(combined_data);
////					mChart.highlightValues(highs);
////				}
////				invalidate();
////
////				turnOnProgress(false);
////				if (mInterface != null) {
////					mInterface.OnPipelineAsyncTaskFinished();
////				}
////			}
////		};
////
////		if (statsPipelineAsyncTask != null) {
////			statsPipelineAsyncTask.cancel(true);
////		}
////		statsPipelineAsyncTask = new StatsPipelineAsyncTask(choice_id, subchoice_ids, date_start, date_end, seller_id, usr_id, period_display, show_obj, taskInterface);
////		statsPipelineAsyncTask.execute();
//	}
//
//	private void turnOnProgress(boolean isVisible) {
//		if (isVisible) {
//			min_height_progress_view.setVisibility(View.VISIBLE);
//			circular_progress.setVisibility(View.VISIBLE);
//			graph_container.setVisibility(View.GONE);
//		} else {
//			min_height_progress_view.setVisibility(View.GONE);
//			circular_progress.setVisibility(View.GONE);
//			graph_container.setVisibility(View.VISIBLE);
//		}
//	}
//
//	public void refresh() {
//		if (mInterface != null) {
//			mInterface.beforeRefresh();
//		}
//		mChart.clearAnimation();
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			mChart.cancelPendingInputEvents();
//		}
////		if (statsPipelineAsyncTask != null) {
////			statsPipelineAsyncTask.cancel(true);
////		}
////		statsPipelineAsyncTask = new StatsPipelineAsyncTask(choice_id, subchoice_ids, date_start, date_end, seller_id, usr_id, period_display, show_obj, taskInterface);
////		statsPipelineAsyncTask.execute();
//	}
//
//	public void invalidate() {
//		if (mChart != null) {
//			mChart.notifyDataSetChanged();
//			mChart.fitScreen();
//			if (charts_datas != null) {
//				mChart.moveViewToX(charts_datas.size());
//			}
//			if (Tools.isTabletSize()) {
//				if (Tools.getOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
//					mChart.setVisibleXRangeMaximum(land_tablet);
//				} else {
//					mChart.setVisibleXRangeMaximum(tablet);
//				}
//			} else {
//				if (Tools.getOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
//					mChart.setVisibleXRangeMaximum(land_mobile);
//				} else {
//					mChart.setVisibleXRangeMaximum(mobile);
//				}
//			}
//			mChart.invalidate();
//		}
//	}
//
//	@Override
//	public void onValueSelected(Entry e, Highlight h) {
//		ArrayList<Integer> al = ((Data) e.getData()).ord_ids_list.get(h.getStackIndex());
//		mChart.highlightValues(highs);
//		if (al != null && al.size() > 0) {
//			Bundle b = new Bundle();
//			b.putIntegerArrayList("selected_ids", al);
//
//			if (h.getStackIndex() > 0) {
//				ArrayList<Integer> states = new ArrayList<>();
//				states.add(Const.ORD.DEVIS);
//				b.putIntegerArrayList("states", states);
//				if (OrdersDb.getInstance().get(b).getCount() <= 0) {
//					return;
//				}
//			}
//
//			FragmentsSupervisor.add(OrdersFragment.class, b);
//		}
//	}
//
//	@Override
//	public void onNothingSelected() {
//		mChart.highlightValues(highs);
//	}
//
//	public List<YContentValues> get_pipeline_turnover(int order_margin_choice, int period_type, int seller_id, int usr_id) {
//		switch (order_margin_choice) {
//			case StatsTotalFilterView.ORDER_MARGE_STAT:
//			case StatsTotalFilterView.INVOICE_MARGE_STAT: {
//				String sql = "";
//				//sql += " select period, total_ca_ht as turnover_pipeline_ht, total_ca_ttc as turnover_pipeline_ttc, marge as margin from ";
//				//sql += "(" + StatsDb.getInstance().get_pipeline_sql(Calendar.getInstance().getTime()/*now*/, null, period_type, seller_id, usr_id) + ")"; //Pour le pipeline seule les donnée supérieures à aujourd'hui nous intéressent
//				//return SqlFactory.queryList(sql);
//				Log.e("Yuto", Thread.currentThread().getStackTrace()[2]+""+sql);
//				return null; //Quel get_pipeline faut-il faire si c'est une marge ?
//			}
//			case StatsTotalFilterView.ORDER_STAT:
//			case StatsTotalFilterView.INVOICE_STAT: {
//				String sql = "";
//				sql += " select period, total_ht as turnover_ht, total_ttc as turnover_ttc, 0 as margin_ht, 0 as margin_ttc from ";
//				Log.e("Yuto", Thread.currentThread().getStackTrace()[2]+"Ajouter une marge ici enlever le 0 "+sql);
//				sql += "(" + StatsDb.getInstance().get_pipeline_sql(Calendar.getInstance().getTime()/*now*/, null, period_type, seller_id, usr_id) + ")"; //Pour le pipeline seule les donnée supérieures à aujourd'hui nous intéressent
//				Log.e("Yuto", Thread.currentThread().getStackTrace()[2]+""+sql);
//				return SqlFactory.queryList(sql);
//			}
//		}
//		return null;
//	}
//
//	private static class StatsPipelineAsyncTask extends AsyncTask<Void, Void, Boolean> {
//
//		LinkedHashMap<String, Data> task_charts_datas;
//
//		private int task_choice_id;
//		private ArrayList<Integer> task_subchoice_ids;
//		private Date task_date_start;
//		private Date task_date_end;
//		private int task_seller_id;
//		private int task_usr_id;
//		private int task_period_display;
//		private boolean task_show_obj;
//
//		private StatsPipelineAsyncTaskInterface taskInterface;
//
//		//Von nous permettre de déterminer la periode évaluée
//		private long turnover_first_date;
//		private long turnover_last_date;
//		private long pipeline_first_date;
//		private long pipeline_last_date;
//
//		StatsPipelineAsyncTask(int choice_id, ArrayList<Integer> subchoice_ids, Date date_start, Date date_end, int seller_id, int usr_id, int periodDisplay, boolean show_obj, StatsPipelineAsyncTaskInterface taskInterface) {
//			this.task_choice_id = choice_id;
//			this.task_subchoice_ids = subchoice_ids;
//			this.task_date_start = date_start;
//			this.task_date_end = date_end;
//			this.task_seller_id = seller_id;
//			this.task_usr_id = usr_id;
//			this.task_period_display = periodDisplay;
//			this.task_show_obj = show_obj;
//			this.taskInterface = taskInterface;
//		}
//
//		@Override
//		protected void onPreExecute() {
//
//		}
//
//		@Override
//		protected Boolean doInBackground(Void... params) {
//			Thread.currentThread().setName(this.getClass().getName());
//
//
//			Double total;
//			ArrayList<Integer> ord_ids;
//			task_charts_datas = new LinkedHashMap<>();
//
//			List<YContentValues> ca_datas = new ArrayList<>();
//
//			switch (task_choice_id) {
//				case StatsTotalFilterView.ORDER_STAT:
//				case StatsTotalFilterView.ORDER_MARGE_STAT: {
//					if (task_subchoice_ids == null) {
//						task_subchoice_ids = new ArrayList<>();
//						task_subchoice_ids.addAll(Const.ORD.finalize_states);
//					}
//					ca_datas = OrdersDb.getInstance().get_average_totals(task_subchoice_ids, task_date_start, task_date_end, task_seller_id, task_usr_id, 0, true, task_period_display, 250);
//					if (!ca_datas.isEmpty()) {
//						turnover_first_date = getFirstDate(ca_datas, "date");
//						turnover_last_date = getLastDate(ca_datas, "date");
//					}
//					break;
//				}
//				case StatsTotalFilterView.INVOICE_STAT:
//				case StatsTotalFilterView.INVOICE_MARGE_STAT: {
//					ca_datas = OrdInvoicesDb.getInstance().get_average_totals(task_date_start, task_date_end, task_seller_id, task_usr_id, 0, true, task_period_display, 250, null);
//					if (!ca_datas.isEmpty()) {
//						turnover_first_date = getFirstDate(ca_datas, "date");
//						turnover_last_date = getLastDate(ca_datas, "date");
//					}
//					break;
//				}
//			}
//
//			if (isCancelled()) {
//				return false;
//			}
//
//			// On ajoute au datas les chiffres d'affaire recupérés
//			Date older_date = new Date();
//			Date lastest_date = new Date();
//
//			for (YContentValues ca_data : ca_datas) {
//
//				ord_ids = new ArrayList<>();
//				String[] split = ca_data.getAsString("ord_ids").replace("\"", "").split(",");
//
//				if (task_choice_id == StatsTotalFilterView.ORDER_MARGE_STAT || task_choice_id == StatsTotalFilterView.INVOICE_MARGE_STAT) {
//					total = ca_data.getAsDouble("marge");
//				} else {
//					total = Const.DISPLAY.is_turnover_ttc() ? ca_data.getAsDouble("total_ca_ttc") : ca_data.getAsDouble("total_ca_ht");
//				}
//
//				if (split.length > 0) {
//					for (String id : split) {
//						if (!id.trim().equals("")) {
//							ord_ids.add(Integer.parseInt(id));
//						}
//					}
//				}
//				Date d = Strings.convertDateEnToJava(ca_data.getAsString("date"));
//				// Dans le cas ou le parametres date_start est égale à null, on récupère la plus ancienne date des chiffres d'affaire
//				if (d.compareTo(older_date) == -1) {
//					older_date = d;
//				}
//				new Data(d, total.floatValue(), ord_ids, false, false, task_charts_datas, task_choice_id, task_seller_id, task_period_display, task_show_obj);
//			}
//
//			if (isCancelled()) {
//				return false;
//			}
//
//			// on ne récupère que les données supérieurs à la date actuel pour les informations de pipes
//			Calendar now = Calendar.getInstance();
//			now.setTime(new Date());
//			Calendar start = Calendar.getInstance();
//			start.setTime(task_date_start == null ? new Date() : task_date_start);
//
//			Date tmp_date_start = start.getTime();
//			if (start.before(now)) {
//				tmp_date_start = now.getTime();
//			}
//
//			List<YContentValues> pipe_datas = StatsDb.getInstance().get_pipeline(tmp_date_start, null, task_period_display, task_seller_id, task_usr_id);
//			if (!pipe_datas.isEmpty()) {
//				pipeline_first_date = getFirstDate(pipe_datas, "fld_delay");
//				pipeline_last_date = getLastDate(pipe_datas, "fld_delay");
//			}
//
//			if (isCancelled()) return false;
//
//			//  On ajoute au datas les statistiques de vente
//			for (YContentValues pipe_data : pipe_datas) {
//
//				ord_ids = new ArrayList<>();
//				String[] split = pipe_data.getAsString("ord_ids").replace("\"", "").split(",");
//
//				total = Const.DISPLAY.is_turnover_ttc() ? pipe_data.getAsDouble("total_ttc") : pipe_data.getAsDouble("total_ht");
//
//				if (split.length > 0) {
//					for (String id : split) {
//						if (!id.trim().equals("")) {
//							ord_ids.add(Integer.parseInt(id));
//						}
//					}
//
//					// On étale l'estimation sur le nombre de mois estimé
//					Date d = Strings.convertDateEnToJava(pipe_data.getAsString("delay"));
//					if (d != null) {
//						Calendar c = Calendar.getInstance();
//						c.setTime(d);
//						if (d.compareTo(lastest_date) == 1) {
//							lastest_date = d;
//						}
//
//						// Date de signature estimée
//						Date pipe_date = Strings.convertDateEnToJava(pipe_data.getAsString("fld_delay"));
//
//						Integer days = Strings.getDateDiff(pipe_date, Calendar.getInstance().getTime(), Calendar.DAY_OF_YEAR);
//						Integer month_delay = Strings.getDateMonth(Calendar.getInstance().getTime(), pipe_date); // calcul le nombre de mois entre la date de fin et maitenant
//
//						if (days > 0 && month_delay > 0) { // si la diff est positive on étale le total par jour sur les mois entre la date de fin et le jour
//							total = total / days;
//
//							new Data(c.getTime(), total.floatValue() * c.get(Calendar.DAY_OF_MONTH), ord_ids, true, false, task_charts_datas, task_choice_id, task_seller_id, task_period_display, task_show_obj);
//
//							for (int i = month_delay; i > 0; i--) {
//
//								c.add(Calendar.MONTH, -1);
//
//								if (Calendar.getInstance().get(Calendar.MONTH) == c.get(Calendar.MONTH)) {
//									new Data(c.getTime(), total.floatValue() * (c.getActualMaximum(Calendar.DAY_OF_MONTH) - Calendar.getInstance().get(Calendar.DAY_OF_MONTH)), ord_ids, true, false, task_charts_datas, task_choice_id, task_seller_id, task_period_display, task_show_obj);
//								} else {
//									new Data(c.getTime(), total.floatValue() * c.getActualMaximum(Calendar.DAY_OF_MONTH), ord_ids, true, false, task_charts_datas, task_choice_id, task_seller_id, task_period_display, task_show_obj);
//								}
//							}
//						} else { // dans le cas contraite l'intégralité du devis est mise à la date de fin
//							new Data(c.getTime(), total.floatValue(), ord_ids, true, false, task_charts_datas, task_choice_id, task_seller_id, task_period_display, task_show_obj);
//						}
//					}
//				}
//			}
//
//			// Ca périodes - 1 an
//
//			// date de début -1
//			Calendar c1 = Calendar.getInstance();
//			if (task_date_start != null) {
//				c1.setTime(task_date_start);
//			} else {
//				c1.setTime(older_date);
//			}
//			c1.add(Calendar.YEAR, -1);
//
//			// date de fin -1
//			Calendar c2 = Calendar.getInstance();
//			if (task_date_end != null) {
//				c2.setTime(task_date_end);
//			} else {
//				c2.setTime(lastest_date);
//			}
//			c2.add(Calendar.YEAR, -1);
//
//
//			// dans le cas d'un affiche en mois/années on prend toute la période en référence
//			if (task_period_display == PeriodDisplayFilterView.MONTH) {
//				c1.set(Calendar.DAY_OF_MONTH, c1.getActualMinimum(Calendar.DAY_OF_MONTH));
//				c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
//			} else if (task_period_display == PeriodDisplayFilterView.YEAR) {
//				c1.set(Calendar.DAY_OF_YEAR, c1.getActualMinimum(Calendar.DAY_OF_YEAR));
//				c2.set(Calendar.DAY_OF_YEAR, c2.getActualMaximum(Calendar.DAY_OF_YEAR));
//			}
//
//			if (isCancelled()) return false;
//
//			List<YContentValues> ca_datas_back = new ArrayList<>();
//			switch (task_choice_id) {
//				case StatsTotalFilterView.ORDER_STAT:
//				case StatsTotalFilterView.ORDER_MARGE_STAT:
//					if (task_subchoice_ids == null) {
//						return false;//S'il sélectionne 'Ca commandé' ou 'Marge commandé' on peut pas faire le calcul si on a pas les 'etats de commande'
//					}
//					ca_datas_back = OrdersDb.getInstance().get_average_totals(task_subchoice_ids, c1.getTime(), c2.getTime(), task_seller_id, task_usr_id, 0, true, task_period_display, 250);
//					break;
//				case StatsTotalFilterView.INVOICE_STAT:
//				case StatsTotalFilterView.INVOICE_MARGE_STAT:
//					ca_datas_back = OrdInvoicesDb.getInstance().get_average_totals(c1.getTime(), c2.getTime(), task_seller_id, task_usr_id, 0, true, task_period_display, 250, null);
//					break;
//			}
//
//			for (YContentValues ca_data_back : ca_datas_back) {
//
//				if (task_choice_id == StatsTotalFilterView.ORDER_MARGE_STAT || task_choice_id == StatsTotalFilterView.INVOICE_MARGE_STAT) {
//					total = ca_data_back.getAsDouble("marge");
//				} else {
//					total = Const.DISPLAY.is_turnover_ttc() ? ca_data_back.getAsDouble("total_ca_ttc") : ca_data_back.getAsDouble("total_ca_ht");
//				}
//				Calendar c = Calendar.getInstance();
//				c.setTime(Strings.convertDateEnToJava(ca_data_back.getAsString("date")));
//				c.add(Calendar.YEAR, 1);
//
//				//Cette classe STATIC Data remplit l'objet task_charts_datas
//				new Data(c.getTime(), total.floatValue(), new ArrayList<Integer>(), false, true, task_charts_datas, task_choice_id, task_seller_id, task_period_display, task_show_obj);
//			}
//
//			// Traitement pour rajouter les périodes vides dans le graph au cas ou il n'y aurait pas de CA ou de pipe
//
//			List<Data> datas = new ArrayList<>(task_charts_datas.values());
//
//			Collections.sort(datas, new Comparator<Data>() {
//				@Override
//				public int compare(Data data, Data t1) {
//					return data.date.compareTo(t1.date);
//				}
//			});
//
//			int nbPeriodsWithZeroAsValue;
//			int unit;
//			long diffInMillisec;
//
//			if (isCancelled()) return false;
//
//			//Détermine la periode observée
//			long period_first_date = Math.min(turnover_first_date, pipeline_first_date);
//			long period_last_date = Math.max(turnover_last_date, pipeline_last_date);
//			ArrayList<String> periodsToDisplay = getPeriodsToBeDisplayed(task_period_display, period_first_date, period_last_date);
//
//			String last_period_added = null;
//
//			LinkedHashMap<String, Data> temp_data = new LinkedHashMap<>(); //On ajoute chaque donnée à temp_data et on ajoute des données avec des valeurs à nulles si une periode manque.
//			for (Data data : datas) {
//				int pos = datas.indexOf(data);
//
//				// Si c'est la première position alors, il n'y a pas de diff avec la date antèrieure
//				if (pos == 0) {
//					String period_label = get_period_string(task_period_display, data.date);
//					temp_data.put(period_label, data);
//					last_period_added = period_label;
//				} else {
//					Calendar loop_date_start = Calendar.getInstance();
//					Calendar loop_date_end = Calendar.getInstance();
//					loop_date_start.setTime(datas.get(pos - 1).date);
//					loop_date_end.setTime(datas.get(pos).date);
//
//					//Ceci devarit être uniformisé mais pour le moment on fait un traitement différent selon le type de période sélectionné
//					boolean calculateDiffToGetNbPeriods = task_period_display == PeriodDisplayFilterView.DAY
//							|| task_period_display == PeriodDisplayFilterView.WEEK
//							|| task_period_display == PeriodDisplayFilterView.YEAR
//							|| task_period_display == PeriodDisplayFilterView.MONTH;
//					boolean compareWithAllPeriodsToGetNbOfPeriods = task_period_display == PeriodDisplayFilterView.QUARTER
//							|| task_period_display == PeriodDisplayFilterView.SEMESTER
//							|| task_period_display == PeriodDisplayFilterView.SEASON;
//
//					if (calculateDiffToGetNbPeriods) {
//						int diffYear = loop_date_end.get(Calendar.YEAR) - loop_date_start.get(Calendar.YEAR);
//						switch (task_period_display) {
//							case PeriodDisplayFilterView.DAY:
//								unit = Calendar.DAY_OF_MONTH;
//								diffInMillisec = loop_date_end.getTimeInMillis() - loop_date_start.getTimeInMillis();
//								nbPeriodsWithZeroAsValue = (int) TimeUnit.MILLISECONDS.toDays(diffInMillisec);
//								break;
//							case PeriodDisplayFilterView.WEEK:
//								unit = Calendar.WEEK_OF_YEAR;
//								diffInMillisec = loop_date_end.getTimeInMillis() - loop_date_start.getTimeInMillis();
//								nbPeriodsWithZeroAsValue = (int) TimeUnit.MILLISECONDS.toDays(diffInMillisec);
//								break;
//							case PeriodDisplayFilterView.YEAR:
//								unit = Calendar.YEAR;
//								nbPeriodsWithZeroAsValue = diffYear;
//								break;
//							case PeriodDisplayFilterView.MONTH:
//							default:
//								unit = Calendar.MONTH;
//								nbPeriodsWithZeroAsValue = diffYear * 12 + loop_date_end.get(Calendar.MONTH) - loop_date_start.get(Calendar.MONTH);
//								break;
//						}
//
//						for (int i = 0; i < nbPeriodsWithZeroAsValue; i++) {
//							loop_date_start.add(unit, 1);
//							Data d = new Data(loop_date_start.getTime(), 0.0f, new ArrayList<Integer>(), false, false, task_charts_datas, task_choice_id, task_seller_id, task_period_display, task_show_obj);
//							temp_data.put(get_period_string(task_period_display, loop_date_start.getTime()), d); //Ajout de la donnée avec valeur vide
//						}
//					} else if (compareWithAllPeriodsToGetNbOfPeriods) {
//						//S'il y a des periodes avant la periode sur laquelle on est il faut les ajouter comme vide
//						String period_label = get_period_string(task_period_display, data.date);
//						ArrayList<String> periods_labels_betweens = get_periods_labels_betweens(last_period_added, period_label, periodsToDisplay);
//						for (String period_label_between : periods_labels_betweens) {
//							//La date n'a pas d'importance ????
//							//Date date_in_the_period=generate_date_in_period(period_label_between, periodsToDisplay);
//							Data d = new Data(period_label_between, 0.0f, new ArrayList<Integer>(), false, false, task_charts_datas, task_choice_id, task_seller_id, task_period_display, task_show_obj);
//							temp_data.put(period_label_between, d); //Ajout de la donnée avec valeur vide
//						}
//					}
//					String period_label = get_period_string(task_period_display, data.date);
//					temp_data.put(period_label, data); //Ajout de la donnée
//					last_period_added = period_label;
//				}
//			}
//
//			if (isCancelled()) return false;
//
//			if (temp_data.size() > 0) {
//				if (task_date_start != null && task_date_end != null) {
//					LinkedHashMap<String, Data> v = new LinkedHashMap<>();
//					v.putAll(temp_data);
//					for (Map.Entry<String, Data> value : v.entrySet()) {
//						if (value.getValue().date.compareTo(task_date_start) < 0 || value.getValue().date.compareTo(task_date_end) > 0) {
//							temp_data.remove(value.getKey());
//						}
//					}
//				}
//
//				task_charts_datas = temp_data;
//			}
//
//			return true;
//		}
//
//
//		//Si on utilise plus cette asyncTask alors on pourra enlever ces methods d'ici
//		private long getFirstDate(List<YContentValues> ca_datas, String date_tag) {
//			long firstDate = 0;
//			if (!ca_datas.isEmpty()) {
//				firstDate = Strings.convertDateEnToJava(ca_datas.get(0).getAsString(date_tag)).getTime();
//			}
//			for (YContentValues data : ca_datas) {
//				long date = Strings.convertDateEnToJava(data.getAsString(date_tag)).getTime();
//				if (date < firstDate) {
//					firstDate = date;
//				}
//			}
//			return firstDate;
//		}
//
//		private long getLastDate(List<YContentValues> ca_datas, String date_tag) {
//			long lastDate = 0;
//			if (!ca_datas.isEmpty()) {
//				lastDate = Strings.convertDateEnToJava(ca_datas.get(0).getAsString(date_tag)).getTime();
//			}
//			for (YContentValues data : ca_datas) {
//				long date = Strings.convertDateEnToJava(data.getAsString(date_tag)).getTime();
//				if (date > lastDate) {
//					lastDate = date;
//				}
//			}
//			return lastDate;
//		}
//
//		private ArrayList<String> get_periods_labels_betweens(String last_label, String current_label, ArrayList<String> labels) {
//			ArrayList<String> label_betweens = new ArrayList<>();
//
//			boolean isLastLabelReached = false;
//			for (String label : labels) {
//
//				if (isLastLabelReached) {
//					if (label.equals(current_label)) {
//						return label_betweens;
//					} else {
//						label_betweens.add(label);
//					}
//				}
//
//				if (label.equals(last_label)) {
//					isLastLabelReached = true;
//				}
//			}
//			return label_betweens;
//		}
//
//		//A supprimer si on utilise plus cette async
//		private ArrayList<String> getPeriodsToBeDisplayed(int task_period_display, long period_first_date, long period_last_date) {
//			switch (task_period_display) {
//				case PeriodDisplayFilterView.QUARTER: {
//
//					return getQuartersLabels(period_first_date, period_last_date);
//				}
//				case PeriodDisplayFilterView.SEMESTER: {
//					return getSemestersLabels(period_first_date, period_last_date);
//				}
//				case PeriodDisplayFilterView.SEASON: {
//					return getSeasonsLabels(period_first_date, period_last_date);
//				}
//			}
//			return null;
//		}
//
//		//A supprimer si on utilise plus cette async
//		private ArrayList<String> getQuartersLabels(long period_first_date, long period_last_date) {
//			ArrayList<Const.QUARTER> quarters = new ArrayList<>();
//			quarters.add(Const.QUARTER.QUARTER1);
//			quarters.add(Const.QUARTER.QUARTER2);
//			quarters.add(Const.QUARTER.QUARTER3);
//			quarters.add(Const.QUARTER.QUARTER4);
//
//			ArrayList<String> quarters_labels = new ArrayList<>();
//			int firstQuarterYear = Integer.valueOf(getYear(new Date(period_first_date), "yyyy"));
//			String firstQuarter = ToolsRes.getQuarter(new Date(period_first_date)).toStringWithYear(firstQuarterYear);
//			int lastQuarterYear = Integer.valueOf(getYear(new Date(period_last_date), "yyyy"));
//			String lastQuarter = ToolsRes.getQuarter(new Date(period_last_date)).toStringWithYear(lastQuarterYear);
//
//			String last_added_quarter = ""; //To avoid null pointer exception
//			int year = firstQuarterYear;
//			while (!last_added_quarter.equals(lastQuarter)) {
//				for (Const.QUARTER quarter : quarters) {
//
//					if (quarter.toStringWithYear(year).equals(firstQuarter)) {
//						quarters_labels.add(firstQuarter);
//						last_added_quarter = firstQuarter;
//					}
//
//					if (quarters_labels.contains(firstQuarter) && !quarter.toStringWithYear(year).equals(firstQuarter)) { //Tant qu'on a pas ajouté le premier label, on ajoute rien. On commence à ajouter que si le premier label a été trouvé
//						quarters_labels.add(quarter.toStringWithYear(year));
//						last_added_quarter = quarter.toStringWithYear(year);
//					}
//					if (last_added_quarter.equals(lastQuarter)) {
//						break;
//					}
//					if (quarter.equals(Const.QUARTER.QUARTER4)) { //Quand on a traité le dernier quarter on passe à l'année suivante.
//						year++;
//					}
//				}
//			}
//			return quarters_labels;
//		}
//
//		//A supprimer si on utilise plus cette async
//		private ArrayList<String> getSemestersLabels(long period_first_date, long period_last_date) {
//			ArrayList<Const.SEMESTER> semesters = new ArrayList<>();
//			semesters.add(Const.SEMESTER.SEMESTER1);
//			semesters.add(Const.SEMESTER.SEMESTER2);
//
//			ArrayList<String> semesters_labels = new ArrayList<>();
//			int firstSemesterYear = Integer.valueOf(getYear(new Date(period_first_date), "yyyy"));
//			String firstSemester = ToolsRes.getSemester(new Date(period_first_date)).toStringWithYear(firstSemesterYear);
//			int lastSemesterYear = Integer.valueOf(getYear(new Date(period_last_date), "yyyy"));
//			String lastSemester = ToolsRes.getSemester(new Date(period_last_date)).toStringWithYear(lastSemesterYear);
//
//			String last_added_semester = ""; //To avoid null pointer exception
//			int year = firstSemesterYear;
//			while (!last_added_semester.equals(lastSemester)) {
//				for (Const.SEMESTER semester : semesters) {
//
//					if (semester.toStringWithYear(year).equals(firstSemester)) {
//						semesters_labels.add(firstSemester);
//						last_added_semester = firstSemester;
//					}
//
//					if (semesters_labels.contains(firstSemester) && !semester.toStringWithYear(year).equals(firstSemester)) { //Tant qu'on a pas ajouté le premier label, on ajoute rien. On commence à ajouter que si le premier label a été trouvé
//						semesters_labels.add(semester.toStringWithYear(year));
//						last_added_semester = semester.toStringWithYear(year);
//					}
//					if (last_added_semester.equals(lastSemester)) {
//						break;
//					}
//					if (semester.equals(Const.SEMESTER.SEMESTER2)) { //Quand on a traité le dernier semester on passe à l'année suivante.
//						year++;
//					}
//				}
//			}
//			return semesters_labels;
//		}
//
//		//A supprimer si on utilise plus cette async
//		private ArrayList<String> getSeasonsLabels(long period_first_date, long period_last_date) {
//			ArrayList<Const.S_SEASON> seasons = new ArrayList<>();
//			seasons.add(Const.S_SEASON.S1_WINTER);
//			seasons.add(Const.S_SEASON.S2_SPRING);
//			seasons.add(Const.S_SEASON.S3_SUMMER);
//			seasons.add(Const.S_SEASON.S4_AUTUMN);
//			seasons.add(Const.S_SEASON.S5_WINTER);
//
//			ArrayList<String> seasons_labels = new ArrayList<>();
//			int firstSeasonYear = Integer.valueOf(getYear(new Date(period_first_date), "yyyy"));
//			String firstSeason = ToolsRes.getSeason(new Date(period_first_date)).toStringWithYear(firstSeasonYear);
//			int lastSeasonYear = Integer.valueOf(getYear(new Date(period_last_date), "yyyy"));
//			String lastSeason = ToolsRes.getSeason(new Date(period_last_date)).toStringWithYear(lastSeasonYear);
//
//			String last_added_season = ""; //To avoid null pointer exception
//			int year = firstSeasonYear;
//			while (!last_added_season.equals(lastSeason)) {
//				for (Const.S_SEASON season : seasons) {
//
//					if (season.toStringWithYear(year).equals(firstSeason)) {
//						seasons_labels.add(firstSeason);
//						last_added_season = firstSeason;
//					}
//
//					if (seasons_labels.contains(firstSeason) && !season.toStringWithYear(year).equals(firstSeason)) { //Tant qu'on a pas ajouté le premier label, on ajoute rien. On commence à ajouter que si le premier label a été trouvé
//						seasons_labels.add(season.toStringWithYear(year));
//						last_added_season = season.toStringWithYear(year);
//					}
//					if (last_added_season.equals(lastSeason)) {
//						break;
//					}
//					if (season.equals(Const.S_SEASON.S5_WINTER)) { //Quand on a traité le dernier season on passe à l'année suivante.
//						year++;
//					}
//				}
//			}
//			return seasons_labels;
//		}
//
//		@Override
//		protected void onPostExecute(Boolean has_data) {
//			super.onPostExecute(has_data);
//			if (!isCancelled()) {
//				if (taskInterface != null) {
//					taskInterface.onTaskFinish(task_charts_datas);
//				}
//			}
//		}
//
//		private static class MyBarDataSet extends BarDataSet {
//
//			List<BarEntry> yVals;
//
//			MyBarDataSet(List<BarEntry> yVals, String label) {
//				super(yVals, label);
//				this.yVals = yVals;
//			}
//
//			@Override
//			public int getColor(int index) {
//				int ind = index / getStackSize();
//				int pos = index - ind * getStackSize();
//				Data d = (Data) yVals.get(ind).getData();
//
//				switch (pos) {
//					case 0:
//
//						if (d.yValues.get(pos) != null && d.goal > d.yValues.get(pos)) {
//							return orange;
//						} else {
//							return green;
//						}
//
//					default:
//						return yellow_dark;
//				}
//			}
//		}
//
//		private static class ValueFormatter implements IValueFormatter {
//			private String mAppendix;
//			private DecimalFormat mFormat;
//			int type;
//
//			ValueFormatter(String appendix, int decimals, int type) {
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
//			}
//
//			@Override
//			public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//				if (type == 0) {
//					if (((Data) entry.getData()).keyOfValue(value) == ((Data) entry.getData()).max_index) {
//						return mFormat.format(entry.getY()) + mAppendix;
//					}
//				} else if (value > 0f || value < 0f) {
//					return mFormat.format(value) + mAppendix;
//				}
//				return "";
//			}
//		}
//
//		public interface StatsPipelineAsyncTaskInterface {
//			void onTaskFinish(LinkedHashMap<String, Data> charts_datas);
//		}
//	}
//
//	private BarData generateBarData(LinkedHashMap<String, Data> task_charts_datas) {
//		int it = 0;
//		List<BarEntry> values = new ArrayList<>();
//		float[] X;
//		for (Map.Entry<String, Data> entry : task_charts_datas.entrySet()) {
//
//			Data charts_data = entry.getValue();
//			X = new float[4];
//			for (int i = 0; i < 4; i++) {
//				if (charts_data.yValues.get(i) != null) {
//					X[i] = charts_data.yValues.get(i);
//				} else {
//					X[i] = 0.0f;
//				}
//			}
//
//			BarEntry b = new BarEntry(it++, X);
//			b.setData(entry.getValue());
//			b.getSumBelow(0);
//			values.add(b);
//		}
//
//		StatsPipelineAsyncTask.MyBarDataSet set = new StatsPipelineAsyncTask.MyBarDataSet(values, "");
//		set.setDrawValues(set.isDrawValuesEnabled());
//		set.setColors(green, orange, yellow_dark);
//		set.setHighLightAlpha(0);
//
//		BarData b = new BarData();
//		if (set.getEntryCount() > 0) {
//			b.addDataSet(set);
//
//			b.setValueTextSize(13f);
//			b.setValueFormatter(new IValueFormatter() {
//				@Override
//				public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//					return "";
//				}
//			});
//			b.setBarWidth(0.7f);
//		}
//
//		return b;
//
//	}
//
//	private LineData generateLineData(LinkedHashMap<String, Data> task_charts_datas) {
//		ArrayList<Entry> values = new ArrayList<>();
//
//		int f = 0;
//		for (Map.Entry<String, Data> entry : task_charts_datas.entrySet()) {
//			Data charts_data = entry.getValue();
//			values.add(new Entry(f++, charts_data.goal.floatValue()));
//		}
//
//		LineDataSet set = new LineDataSet(values, "");
//		set.setColor(ToolsRes.getColor(R.color.black));
//		set.setDrawHighlightIndicators(false);
//		set.disableDashedHighlightLine();
//		set.setCircleColor(ToolsRes.getColor(R.color.txt_color));
//		set.setDrawCircleHole(false);
//		set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//		set.setLineWidth(0f);
//
//		if (set.getYMax() > 0) {
//			LineData d = new LineData();
//			d.addDataSet(set);
//			d.setValueTextSize(8f);
//			d.setValueFormatter(new StatsPipelineAsyncTask.ValueFormatter(" €", 2, 1));
//			d.setHighlightEnabled(false);
//			return d;
//		}
//
//		return null;
//	}
//
//	/**
//	 * Cette clase permet de convertir et d'organiser les données brut de la BDD en données compatible avec le GRAPH
//	 */
//	private static class Data {
//
//		private String label = null;
//		private String xValue;
//		private LinkedHashMap<Integer, Float> yValues;
//		private LinkedHashMap<Integer, ArrayList<Integer>> ord_ids_list;
//		private int max_index = -1;
//		private Date date;
//		private Double goal = 0.0;
//		Float last_year_total;
//
//		Data(String label, Float yValue, ArrayList<Integer> ord_ids, Boolean is_pipe, Boolean is_last_year,
//		     LinkedHashMap<String, Data> task_charts_datas, int choice_id, int seller_id, int period_display, boolean show_obj
//		) {
//			this(new Date(0), yValue, ord_ids, is_pipe, is_last_year, task_charts_datas, choice_id, seller_id, period_display, show_obj);
//			xValue = label;
//		}
//
//		Data(Date xAxisValue, Float yValue, ArrayList<Integer> ord_ids, Boolean is_pipe, Boolean is_last_year,
//		     LinkedHashMap<String, Data> task_charts_datas, int choice_id, int seller_id, int period_display, boolean show_obj
//		) {
//
//
//			//Ce n'est pas clair ce que signifie cette date. Elle est récupéré en faisant ca_data.getAsString("date") apres avoir lancé la requete OrderDb.get_average_totals_sql. A priori comme il y a un order by date.
//			//Ca doit correspondre à la date la commande la plus récente de la periode donnée cad la dernière commande de la periode.
//			date = xAxisValue;
//			if (date == null && label == null) {
//				return;
//			}
//
//			xValue = get_period_string(period_display, date);
//
//			yValues = new LinkedHashMap<>();
//			ord_ids_list = new LinkedHashMap<>();
//
//			// Uniquement pour le CA  n - 1
//			if (is_last_year) {
//				if (task_charts_datas.containsKey(xValue)) {
//					task_charts_datas.get(xValue).last_year_total = yValue;
//				}
//				return;
//			} else if (is_pipe) { // Dans le cas ou le data serai un ca estimé du pipe commercial
//				// Si les données contiennent déja un chiffre d'affaire
//				if (task_charts_datas.containsKey(xValue)) {
//					task_charts_datas.get(xValue).addYvalue(yValue, ord_ids, 1);
//					return;
//				} else {  // On ajoute un chiffres d'affaire vide
//					addYvalue(0.0f, new ArrayList<Integer>(), 0);
//					addYvalue(yValue, ord_ids, 1);
//				}
//			} else {
//				addYvalue(yValue, ord_ids, 0);
//			}
//
//			task_charts_datas.put(xValue, this);
//
//			if (show_obj && period_display == PeriodDisplayFilterView.MONTH) {
//
//				int usr_id; //seller id
//				YContentValues user = UsersDb.getInstance().get_by_seller_id(seller_id);
//				if (user != null && seller_id > 0) {
//					usr_id = user.getAsInteger("usr_id", 0);
//				} else {
//					usr_id = 0;
//				}
//				if (choice_id == StatsTotalFilterView.ORDER_MARGE_STAT || choice_id == StatsTotalFilterView.INVOICE_MARGE_STAT) {
//					goal = ConfigDb.getDouble("margin_goal_month_" + Strings.getMonth(date, "M"), 0.0, true, usr_id);
//					if (goal <= 0) {
//						goal = ConfigDb.getDouble("margin_goal_month_" + Strings.getMonth(date, "M"), 0.0, true, 0);
//					}
//				} else {
//					goal = ConfigDb.getDouble("turnover_goal_month_" + Strings.getMonth(date, "M"), 0.0, true, usr_id);
//					if (goal <= 0) {
//						goal = ConfigDb.getDouble("turnover_goal_month_" + Strings.getMonth(date, "M"), 0.0, true, 0);
//					}
//				}
//			}
//		}
//
//
//		private void addYvalue(Float y, ArrayList<Integer> ord_ids, Integer part) {
//			max_index++;
//			if (yValues.get(part) != null) {
//				float tmp = yValues.get(part) + y;
//				yValues.put(part, tmp);
//
//				ArrayList<Integer> tmparray = ord_ids_list.get(part);
//				tmparray.addAll(ord_ids);
//				ord_ids_list.put(part, tmparray);
//			} else {
//				yValues.put(part, y);
//				ord_ids_list.put(part, ord_ids);
//			}
//		}
//
//		Integer keyOfValue(float f) {
//			for (Map.Entry<Integer, Float> entry : yValues.entrySet()) {
//				if (entry.getValue() == f) {
//					return entry.getKey();
//				}
//			}
//			return -1;
//		}
//	}
//
//	/**
//	 * Custom implementation of the MarkerView.
//	 */
//	public static class MyMarkerView extends MarkerView {
//
//		private TextView total_val;
//		private TextView percent_val;
//
//		public MyMarkerView(Context context, int layoutResource) {
//
//			super(context, layoutResource);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//			lp.gravity = Gravity.CENTER_HORIZONTAL;
//			//lp.setMargins(0, Tools.dip(2), 0, Tools.dip(2));
//			setLayoutParams(lp);
//
//
//			setPadding(0, 0, 0, 0);
//
//			total_val = (TextView) findViewById(R.id.total_val);
//			percent_val = (TextView) findViewById(R.id.percent_val);
//		}
//
//		// callbacks everytime the MarkerView is redrawn, can be used to update the
//		// content (user-interface)
//		@Override
//		public void refreshContent(Entry e, Highlight highlight) {
//			float y = e.getY();
//
////			Double percent =
//			total_val.setText(Strings.viewPrice(Double.valueOf(String.valueOf(y)), true));
//			percent_val.setVisibility(View.GONE);
//			if (e.getData() != null && ((Data) e.getData()).last_year_total != null) {
//
//				percent_val.setVisibility(View.VISIBLE);
//				float last_year_total = ((Data) e.getData()).last_year_total;
//
//				float diff = y - last_year_total;
//				Double percent = (diff / last_year_total) * 100.0;
//
//				percent_val.setVisibility(View.VISIBLE);
//				if (diff < 0) {
//					percent_val.setTextColor(ToolsRes.getColor(R.color.error));
//				} else {
//					percent_val.setTextColor(ToolsRes.getColor(R.color.success));
//				}
//				percent_val.setText("(" + (percent > 0 ? "+" : "") + Strings.viewPrice(percent, false) + "%)");
//			}
//
//			super.refreshContent(e, highlight);
//		}
//
//		@Override
//		public MPPointF getOffset() {
//			return new MPPointF(-(getWidth() / 2), -getHeight());
//		}
//
//	}
//
//	public View getView() {
//		return _layout;
//	}
//
//
//	/**
//	 * Recupère le nom de la periode
//	 */
//	private static String get_period_string(int periodDisplay, Date date) {
//
//		switch (periodDisplay) {
//			case PeriodDisplayFilterView.DAY:
//				return Strings.getDay(date, "EEE") + " " + Strings.getDay(date) + "/" + Strings.getMonth(date, "MM");
//			case PeriodDisplayFilterView.WEEK:
//				return ToolsRes.getString(R.string.week_short) + " " + Strings.getWeek(date);
//			case PeriodDisplayFilterView.SEASON:
//				return ToolsRes.getSeason(date).toString() + "-" + getYear(date, "yy");
//			case PeriodDisplayFilterView.YEAR:
//				return getYear(date, "yyyy");
//			case PeriodDisplayFilterView.QUARTER:
//				return ToolsRes.getQuarter(date).toString() + "-" + getYear(date, "yy");
//			case PeriodDisplayFilterView.SEMESTER:
//				return ToolsRes.getSemester(date).toString() + "-" + getYear(date, "yy");
//			case PeriodDisplayFilterView.MONTH:
//			default:
//				return Strings.getMonth(date, "MMM") + "-" + getYear(date, "yy");
//		}
//	}
//
//	/**
//	 * Interface
//	 */
//	public interface PipelineDisplayInterface {
//		void OnPipelineAsyncTaskFinished();
//
//		boolean onTouchEvent(MotionEvent event);
//
//		void beforeRefresh();
//	}
//
//	/**
//	 * Permet le changement des drawable en fonction du ratio
//	 *
//	 * @param seekBar Barre de progression
//	 */
//	public static void seekbarPercentbackground(SeekBar seekBar) {
//		if (seekBar.getProgress() > 6) {
//			seekBar.setProgressDrawable(ToolsRes.getDrawable(R.drawable.seekbar_success));
//			seekBar.setThumb(ToolsRes.getDrawable(R.drawable.seekbar_thumb_success));
//		} else if (seekBar.getProgress() > 3) {
//			seekBar.setProgressDrawable(ToolsRes.getDrawable(R.drawable.seekbar_warning));
//			seekBar.setThumb(ToolsRes.getDrawable(R.drawable.seekbar_thumb_warning));
//		} else if (seekBar.getProgress() == 0) {
//			seekBar.setProgressDrawable(ToolsRes.getDrawable(R.drawable.seekbar_pipe_error));
//			seekBar.setThumb(ToolsRes.getDrawable(R.drawable.seekbar_thumb_error));
//		} else {
//			seekBar.setProgressDrawable(ToolsRes.getDrawable(R.drawable.seekbar_error));
//			seekBar.setThumb(ToolsRes.getDrawable(R.drawable.seekbar_thumb_error));
//		}
//	}
//
//	/**
//	 * Permet le changement des drawable en fonction du ratio
//	 *
//	 * @param seekBar barre de progression
//	 * @param delay   liste des délais possible
//	 */
//	public static void seekbarDelaybackground(SeekBar seekBar, List<YContentValues> delay) {
//		if (seekBar.getProgress() == 0 || seekBar.getProgress() >= ((delay.size() / 3) * 2)) {
//			seekBar.setProgressDrawable(ToolsRes.getDrawable(R.drawable.seekbar_error));
//			seekBar.setThumb(ToolsRes.getDrawable(R.drawable.seekbar_thumb_error));
//		} else if (seekBar.getProgress() >= (delay.size() / 3)) {
//			seekBar.setProgressDrawable(ToolsRes.getDrawable(R.drawable.seekbar_warning));
//			seekBar.setThumb(ToolsRes.getDrawable(R.drawable.seekbar_thumb_warning));
//		} else {
//			seekBar.setProgressDrawable(ToolsRes.getDrawable(R.drawable.seekbar_success));
//			seekBar.setThumb(ToolsRes.getDrawable(R.drawable.seekbar_thumb_success));
//		}
//	}
//
//	/**
//	 * Permet le changement du background en fonction du ratio
//	 *
//	 * @param seekBar  Barre de progression
//	 * @param progress Progression en cours
//	 */
//	public static void progressbackground(LinearLayout seekBar, int progress) {
//		if (progress > 6) {
//			seekBar.setBackgroundColor(ToolsRes.getColor(R.color.success));
//		} else if (progress > 3) {
//			seekBar.setBackgroundColor(ToolsRes.getColor(R.color.warning));
//		} else {
//			seekBar.setBackgroundColor(ToolsRes.getColor(R.color.error));
//		}
//	}
//
//	public class CustomCombinedChart extends CombinedChart {
//
//		public CustomCombinedChart(Context context) {
//			super(context);
//		}
//
//		@Override
//		public boolean onTouchEvent(MotionEvent event) {
//
//			if (mInterface != null) {
//				mInterface.onTouchEvent(event);
//				super.onTouchEvent(event);
//			}
//			return getData() != null;
//		}
//
//		@Override
//		public boolean onDragEvent(DragEvent event) {
//			return super.onDragEvent(event);
//		}
//
//		@Override
//		public void setData(CombinedData data) {
//			clearAnimation();
//			super.setData(data);
//		}
//	}
//
//
//	private class TotalTurnOverAsyncTask extends AsyncTask<Bundle, Void, List<YContentValues>> {
//		@Override
//		protected List<YContentValues> doInBackground(Bundle... params) {
//
//			Bundle bundle = params[0];
//			int seller_id = bundle.getInt("seller_id"); //Résultat du filtre seller
//			int usr_id = bundle.getInt("usr_id"); //??
//			Date date_start = Strings.convertDateEnToJava(bundle.getString("date_start")); //Résultat filtre 'Toute la periode'
//			Date date_end = Strings.convertDateEnToJava(bundle.getString("date_end")); //Résultat filtre 'Toute la periode'
//			int order_margin_choice = bundle.getInt("order_margin_choice"); //Résultat filtre 'Chiffre d'affaire commandé'
//			ArrayList<Integer> order_states = bundle.getIntegerArrayList("order_states"); //Résultat filtre 'Etat de commande'
//			int period_type = bundle.getInt("period_type"); //Résultat filtre 'Jour, Semaine, Mois, etc'
//
//			//On récupère le chiffre d'affaire réalisé et le chiffre d'affaire prévisionnel dans 2 requetes différentes
//			List<YContentValues> turnover = get_turnover(order_margin_choice, order_states, date_start, date_end, seller_id, usr_id, period_type);
//			List<YContentValues> pipeline = get_pipeline_turnover(order_margin_choice, period_type, seller_id, usr_id);
//
//			//En fonction des requetes on détermine la periode affichée
//			String[] bounds=getFirstAndLastPeriodLabel(turnover, pipeline);
//			ArrayList<String> period_labels = generatePeriodLabels(period_type, bounds[0], bounds[1]);
//			List<YContentValues> periods = getPeriodsContentValues(period_labels);
//
//			//Merge les 3 requetes et pour obtenir un YContentValue contenant les sommes turnover et pipeline + toutes les periodes : total_turnover_pipeline
//			List<YContentValues> total_turnover_pipeline = get_total_turnover_pipeline(turnover, pipeline, periods);
//
//			//Compléter avec les données qui ont disparus (quand on fait une somme en sql, on ne peut pas afficher les données additionnée dans la requete, il faut donc les ajouter ici)
//			total_turnover_pipeline = complete_turnover_pipeline(total_turnover_pipeline, turnover, pipeline);
//
//			//Compléter : ajouter les colonnes qui compare le resultat à l'année précedente
//			total_turnover_pipeline = add_compare_previous_year_columns(total_turnover_pipeline);
//
//			//Compléter : ajouter les colonnes qui correspondants aux objectifs
//			total_turnover_pipeline = add_goals_columns(total_turnover_pipeline, order_margin_choice, period_type, seller_id);
//
//			return total_turnover_pipeline;
//		}
//
//		@Override
//		protected void onPostExecute(List<YContentValues> aVoid) {
//			super.onPostExecute(aVoid);
//			Toast.makeText(Const.main_activity, "Done", Toast.LENGTH_SHORT).show();
//		}
//
//	}
//
//	private String[] getFirstAndLastPeriodLabel(List<YContentValues> turnover, List<YContentValues> pipeline) {
//		String turnover_first_date = null;
//		String turnover_last_date= null;
//		String pipeline_first_date= null;
//		String pipeline_last_date= null;
//		if(!turnover.isEmpty()){
//			turnover_first_date = turnover.get(0).getAsString("period");
//			turnover_last_date = turnover.get(turnover.size()-1).getAsString("period");
//		}
//		if(!pipeline.isEmpty()){
//			pipeline_first_date = pipeline.get(0).getAsString("period");
//			pipeline_last_date = pipeline.get(pipeline.size()-1).getAsString("period");
//		}
//		ArrayList<String> bounds_candidates=new ArrayList<>();
//		if(turnover_first_date !=null){
//			bounds_candidates.add(turnover_first_date);
//		}
//		if(turnover_last_date !=null){
//			bounds_candidates.add(turnover_last_date);
//		}
//		if(pipeline_first_date !=null){
//			bounds_candidates.add(pipeline_first_date);
//		}
//		if(pipeline_last_date !=null){
//			bounds_candidates.add(pipeline_last_date);
//		}
//
//		Collections.sort(bounds_candidates);
//		return new String[]{bounds_candidates.get(0), bounds_candidates.get(bounds_candidates.size()-1)};
//	}
//
//	private List<YContentValues> get_total_turnover_pipeline(List<YContentValues> turnover, List<YContentValues> pipeline, List<YContentValues> periods) {
//		String sql = "select period, turnover_ht as total_turnover_ht, turnover_ttc as total_turnover_ttc, margin_ht as total_margin_ht, margin_ttc as total_margin_ttc from (";
//		sql += " select * from ("+YContentValues.toSql(turnover)+") union ";
//		sql += " select * from ("+YContentValues.toSql(pipeline)+") union ";
//		sql += " select * from ("+YContentValues.toSql(periods)+")) group by period ";
//		Log.e("Yuto", Thread.currentThread().getStackTrace()[2]+""+sql);
//		return SqlFactory.queryList(sql);
//	}
//
//	private List<YContentValues> get_turnover(int order_margin_choice, ArrayList<Integer> order_states, Date date_start, Date date_end, int seller_id, int usr_id, int period_type) {
//		switch (order_margin_choice) {
//			case StatsTotalFilterView.ORDER_MARGE_STAT:
//			case StatsTotalFilterView.INVOICE_MARGE_STAT: {
//				String sql = "";
//				sql += " select period, total_ca_ht as turnover_ht, total_ca_ttc as turnover_ttc, marge as margin_ht, 0 as margin_ttc from ";
//				sql += "(" + OrdInvoicesDb.getInstance().get_average_totals(date_start, date_end, seller_id, usr_id, 0, true, period_type, 250, null) + ")";
//				Log.e("Yuto", Thread.currentThread().getStackTrace()[2]+""+sql);
//				return SqlFactory.queryList(sql);
//			}
//			case StatsTotalFilterView.ORDER_STAT:
//			case StatsTotalFilterView.INVOICE_STAT: {
//				String sql = "";
//				sql += " select period, total_ca_ht as turnover_ht, total_ca_ttc as turnover_ttc, marge as margin_ht, 0 as margin_ttc from ";
//				sql += "(" + OrdersDb.getInstance().get_average_totals_sql(order_states, date_start, date_end, seller_id, usr_id, 0, true, period_type, 250, null) + ")";
//				Log.e("Yuto", Thread.currentThread().getStackTrace()[2]+""+sql);
//				return SqlFactory.queryList(sql);
//			}
//		}
//		return null;
//	}
//
//
//	private List<YContentValues> add_goals_columns(List<YContentValues> total_turnover_pipeline, int order_margin_choice, int period_type, int seller_id) {
//		for (YContentValues aTotalTurnoverPipeline : total_turnover_pipeline) {
//			String[] goalTags = generateGoalTag(order_margin_choice, period_type, aTotalTurnoverPipeline.getAsString("period"));
//			Double goal_ht = getGoal(goalTags[0], seller_id);
//			Double goal_ttc = getGoal(goalTags[1], seller_id);
//			switch (order_margin_choice) {
//				case StatsTotalFilterView.ORDER_MARGE_STAT:
//				case StatsTotalFilterView.INVOICE_MARGE_STAT: {
//					aTotalTurnoverPipeline.put("goal_margin_ht", goal_ht);
//					aTotalTurnoverPipeline.put("goal_margin_ttc", goal_ttc);
//					break;
//				}
//				case StatsTotalFilterView.ORDER_STAT:
//				case StatsTotalFilterView.INVOICE_STAT: {
//					aTotalTurnoverPipeline.put("goal_turnover_ht", goal_ht);
//					aTotalTurnoverPipeline.put("goal_turnover_ttc", goal_ttc);
//					break;
//				}
//			}
//		}
//		return total_turnover_pipeline;
//	}
//
//	private Double getGoal(String tag, int seller_id) {
//		YContentValues user = UsersDb.getInstance().get_by_seller_id(seller_id);
//		int usr_id = user != null && seller_id > 0 ? user.getAsInteger("usr_id", 0) : 0;
//		Double goal = ConfigDb.getDouble(tag, 0.0, true, usr_id); //Objectif pour ce commercial
//		if (goal <= 0) { //Si on ne trouve pas d'objectif pour ce commercial on prend l'objectif par défaut
//			goal = ConfigDb.getDouble(tag, 0.0, true, 0);
//		}
//		if (goal <= 0) { //Si on ne trouve pas d'objectif par défaut, il n'affiche pas l'objectif
//			return null;
//		}
//		return null;
//	}
//
//	/**
//	 * Cette methode génére les tags qui permette de retrouver les objectifs mentionner dans la variable de config
//	 * Exple :
//	 * margin_goal_06_15 : fixe l'objectif de marge pour le 15 juin
//	 * margin_goal_week_25 : fixe l'objectif de marge pour la semaine 25
//	 * margin_goal_08 : fixe l'objectif de marge pour le mois d'aout
//	 * margin_goal_Q4 : fixe l'objectif de marge pour le quatrième trimestre
//	 * margin_goal_S2 : fixe l'objectif de marge pour le deuxième semestre
//	 * margin_goal_summer : fixe l'objectif de marge pour l'été
//	 * turnover_goal_06_15 : fixe l'objectif de chiffre d'affaire pour le 15 juin
//	 * turnover_goal_week_25 : fixe l'objectif  de chiffre d'affaire pour la semaine 25
//	 * turnover_goal_08 : fixe l'objectif  de chiffre d'affaire pour le mois d'aout
//	 * turnover_goal_Q4 : fixe l'objectif  de chiffre d'affaire pour le quatrième trimestre
//	 * turnover_goal_S2 : fixe l'objectif  de chiffre d'affaire pour le deuxième semestre
//	 * turnover_goal_summer : fixe l'objectif  de chiffre d'affaire pour l'été
//	 * <p>
//	 * IMPORTANT : Cette méthode ne génère pas de string particulier pour le HT et le TTC pour le  moment.
//	 * L'étiquette qu'elle retourne ne gère que le HT.
//	 *
//	 * @param choice_id
//	 * @param period
//	 * @param period_label
//	 * @return le tag (marge ou turnover) ht et le tag ttc
//	 */
//	private String[] generateGoalTag(int choice_id, int period, String period_label) {
//		String goalTag = "";
//		switch (choice_id) {
//			case StatsTotalFilterView.ORDER_MARGE_STAT:
//			case StatsTotalFilterView.INVOICE_MARGE_STAT: {
//				goalTag = "margin_goal_";
//				break;
//			}
//			case StatsTotalFilterView.ORDER_STAT:
//			case StatsTotalFilterView.INVOICE_STAT: {
//				goalTag = "turnover_goal_";
//				break;
//			}
//		}
//
//		switch (period) {
//			case PeriodDisplayFilterView.DAY:
//				goalTag += "day_" + period_label.substring(4, period_label.length());
//				break;
//			case PeriodDisplayFilterView.WEEK:
//				goalTag += "week_" + period_label.substring(4, period_label.length());
//				break;
//			case PeriodDisplayFilterView.MONTH:
//				goalTag += "month_" + period_label.substring(4, period_label.length());
//				break;
//			case PeriodDisplayFilterView.QUARTER:
//				goalTag += "quarter_" + period_label.substring(4, period_label.length());
//				break;
//			case PeriodDisplayFilterView.SEMESTER:
//				goalTag += "semester_" + period_label.substring(4, period_label.length());
//				break;
//			case PeriodDisplayFilterView.YEAR:
//				goalTag += "year_" + period_label.substring(0, 3);
//				break;
//			case PeriodDisplayFilterView.SEASON:
//				goalTag += "season_" + period_label.substring(4, period_label.length());
//				break;
//		}
//		return new String[]{goalTag, null};
//	}
//
//	private List<YContentValues> add_compare_previous_year_columns(List<YContentValues> total_turnover_pipeline) {
//		for (YContentValues aTotalTurnoverPipeline : total_turnover_pipeline) {
//
//			//On récupere le TotalTurnoverPipeline de l'année précédente
//			String period = aTotalTurnoverPipeline.getAsString("period");
//			int year = Integer.valueOf(period.substring(0, 3));
//			int lastYear = year - 1;
//			String lastPeriod = String.valueOf(lastYear) + "" + period.substring(4, period.length());
//			YContentValues lastYearTotalTurnoverPipeline = findTotalTurnoverPipeline(total_turnover_pipeline, lastPeriod);
//
//			//Ajoute les colonnes avec le diff en % par rapport à l'année précedente
//			int diff_previous_year_total_turnover_ht = aTotalTurnoverPipeline.getAsInteger("total_turnover_ht") - lastYearTotalTurnoverPipeline.getAsInteger("total_turnover_ht");
//			int diff_previous_year_total_turnover_ttc = aTotalTurnoverPipeline.getAsInteger("total_turnover_ttc") - lastYearTotalTurnoverPipeline.getAsInteger("total_turnover_ttc");
//			int diff_previous_year_margin_ht = aTotalTurnoverPipeline.getAsInteger("total_margin_ht") - lastYearTotalTurnoverPipeline.getAsInteger("total_margin_ht");
//			int diff_previous_year_margin_ttc = aTotalTurnoverPipeline.getAsInteger("total_margin_ttc") - lastYearTotalTurnoverPipeline.getAsInteger("total_margin_ttc");
//
//			double pct_diff_previous_year_total_turnover_ht = (diff_previous_year_total_turnover_ht * 100) / lastYearTotalTurnoverPipeline.getAsInteger("total_turnover_ht");
//			double pct_diff_previous_year_total_turnover_ttc = (diff_previous_year_total_turnover_ttc * 100) / lastYearTotalTurnoverPipeline.getAsInteger("total_turnover_ttc");
//			double pct_diff_previous_year_margin_ht = (diff_previous_year_margin_ht * 100) / lastYearTotalTurnoverPipeline.getAsInteger("total_margin_ht");
//			double pct_diff_previous_year_margin_ttc = (diff_previous_year_margin_ttc * 100) / lastYearTotalTurnoverPipeline.getAsInteger("total_margin_ttc");
//
//			aTotalTurnoverPipeline.put("pct_diff_previous_year_total_turnover_ht", pct_diff_previous_year_total_turnover_ht);
//			aTotalTurnoverPipeline.put("pct_diff_previous_year_total_turnover_ttc", pct_diff_previous_year_total_turnover_ttc);
//			aTotalTurnoverPipeline.put("pct_diff_previous_year_margin_ht", pct_diff_previous_year_margin_ht);
//			aTotalTurnoverPipeline.put("pct_diff_previous_year_margin_ttc", pct_diff_previous_year_margin_ttc);
//		}
//		return total_turnover_pipeline;
//	}
//
//	private YContentValues findTotalTurnoverPipeline(List<YContentValues> total_turnover_pipeline, String period) {
//		for (YContentValues aTotalTurnoverPipeline : total_turnover_pipeline) {
//			if (aTotalTurnoverPipeline.getAsString("period").equals(period)) {
//				return aTotalTurnoverPipeline;
//			}
//		}
//		return null;
//	}
//
//	private List<YContentValues> complete_turnover_pipeline(List<YContentValues> total_turnover_pipeline, List<YContentValues> turnover, List<YContentValues> pipeline) {
//		for (YContentValues aTotalTurnoverPipeline : total_turnover_pipeline) {
//			//Ajoute les donnée manquantes du turnover
//			YContentValues correspondingTurnover = getCorrespondingTurnover(aTotalTurnoverPipeline, turnover);
//			if (correspondingTurnover != null) {
//				aTotalTurnoverPipeline.put("turnover_ht", correspondingTurnover.getAsString("total_ca_ht"));
//				aTotalTurnoverPipeline.put("turnover_ttc", correspondingTurnover.getAsString("total_ca_ttc"));
//				aTotalTurnoverPipeline.put("margin_ht", correspondingTurnover.getAsString("margin_ht")); //Pour le moment c'est marge
//				aTotalTurnoverPipeline.put("margin_ttc", correspondingTurnover.getAsString("margin_ht"));
//			} else {
//				aTotalTurnoverPipeline.put("turnover_ht", 0);
//				aTotalTurnoverPipeline.put("turnover_ttc", 0);
//				aTotalTurnoverPipeline.put("margin_ht", 0);
//				aTotalTurnoverPipeline.put("margin_ttc", 0);
//			}
//
//			//Ajoute les donnée manquantes du pipeline
//			YContentValues correspondingPipeline = getCorrespondingPipeline(aTotalTurnoverPipeline, pipeline);
//			if (correspondingTurnover != null) {
//				aTotalTurnoverPipeline.put("pipeline_turnover_ht", correspondingPipeline.getAsString("total_ht"));
//				aTotalTurnoverPipeline.put("pipeline_turnover_ttc", correspondingPipeline.getAsString("total_ht"));
//				aTotalTurnoverPipeline.put("pipeline_margin_ht", correspondingPipeline.getAsString("margin_ht"));
//				aTotalTurnoverPipeline.put("pipeline_margin_ttc", correspondingPipeline.getAsString("margin_ht"));
//			} else {
//				aTotalTurnoverPipeline.put("pipeline_turnover_ht", 0);
//				aTotalTurnoverPipeline.put("pipeline_turnover_ttc", 0);
//				aTotalTurnoverPipeline.put("pipeline_margin_ht", 0);
//				aTotalTurnoverPipeline.put("pipeline_margin_ttc", 0);
//			}
//		}
//		return total_turnover_pipeline;
//	}
//
//	private YContentValues getCorrespondingPipeline(YContentValues aTotalTurnoverPipeline, List<YContentValues> pipeline) {
//		for (YContentValues aPipeline : pipeline) {
//			if (aPipeline.getAsString("period").equals(aTotalTurnoverPipeline.getAsString("period"))) {
//				return aPipeline;
//			}
//		}
//		return null;
//	}
//
//	private YContentValues getCorrespondingTurnover(YContentValues aTotalTurnoverPipeline, List<YContentValues> turnover) {
//		for (YContentValues aTurnover : turnover) {
//			if (aTurnover.getAsString("period").equals(aTotalTurnoverPipeline.getAsString("period"))) {
//				return aTurnover;
//			}
//		}
//		return null;
//	}
//
//	private List<YContentValues> getPeriodsContentValues(ArrayList<String> periodsToDisplay) {
//		ArrayList<YContentValues> periodsContentValues = new ArrayList<>();
//		for (String period_label : periodsToDisplay) {
//			YContentValues periodContentValues = new YContentValues();
//			periodContentValues.put("period", period_label);
//			periodContentValues.put("turnover_ht", 0);
//			periodContentValues.put("turnover_ttc", 0);
//			periodContentValues.put("margin_ht", 0);
//			periodContentValues.put("margin_ttc", 0);
//			periodsContentValues.add(periodContentValues);
//		}
//		return periodsContentValues;
//	}
//
//	private long getFirstDate(List<YContentValues> ca_datas, String date_tag) {
//		long firstDate = 0;
//		if (!ca_datas.isEmpty()) {
//			firstDate = Strings.convertDateEnToJava(ca_datas.get(0).getAsString(date_tag)).getTime();
//		}
//		for (YContentValues data : ca_datas) {
//			long date = Strings.convertDateEnToJava(data.getAsString(date_tag)).getTime();
//			if (date < firstDate) {
//				firstDate = date;
//			}
//		}
//		return firstDate;
//	}
//
//	private long getLastDate(List<YContentValues> ca_datas, String date_tag) {
//		long lastDate = 0;
//		if (!ca_datas.isEmpty()) {
//			lastDate = Strings.convertDateEnToJava(ca_datas.get(0).getAsString(date_tag)).getTime();
//		}
//		for (YContentValues data : ca_datas) {
//			long date = Strings.convertDateEnToJava(data.getAsString(date_tag)).getTime();
//			if (date > lastDate) {
//				lastDate = date;
//			}
//		}
//		return lastDate;
//	}
//
//	private ArrayList<String> generatePeriodLabels(int task_period_display, String first_period, String last_period) {
//		switch (task_period_display) {
//			case PeriodDisplayFilterView.QUARTER: {
//				return getQuartersLabels(first_period, last_period);
//			}
//			case PeriodDisplayFilterView.SEMESTER: {
//				return getSemestersLabels(first_period, last_period);
//			}
//			case PeriodDisplayFilterView.SEASON: {
//				return getSeasonsLabels(first_period, last_period);
//			}
//		}
//		return null;
//	}
//
//	private ArrayList<String> getQuartersLabels(String firstQuarter, String lastQuarter) {
//		ArrayList<Const.QUARTER> quarters = new ArrayList<>();
//		quarters.add(Const.QUARTER.QUARTER1);
//		quarters.add(Const.QUARTER.QUARTER2);
//		quarters.add(Const.QUARTER.QUARTER3);
//		quarters.add(Const.QUARTER.QUARTER4);
//
//		ArrayList<String> quarters_labels = new ArrayList<>();
//		int firstQuarterYear = Integer.valueOf(firstQuarter.substring(0,3));
//
//		String last_added_quarter = ""; //To avoid null pointer exception
//		int year = firstQuarterYear;
//		while (!last_added_quarter.equals(lastQuarter)) {
//			for (Const.QUARTER quarter : quarters) {
//
//				if (quarter.toStringWithYear(year).equals(firstQuarter)) {
//					quarters_labels.add(firstQuarter);
//					last_added_quarter = firstQuarter;
//				}
//
//				if (quarters_labels.contains(firstQuarter) && !quarter.toStringWithYear(year).equals(firstQuarter)) { //Tant qu'on a pas ajouté le premier label, on ajoute rien. On commence à ajouter que si le premier label a été trouvé
//					quarters_labels.add(quarter.toStringWithYear(year));
//					last_added_quarter = quarter.toStringWithYear(year);
//				}
//				if (last_added_quarter.equals(lastQuarter)) {
//					break;
//				}
//				if (quarter.equals(Const.QUARTER.QUARTER4)) { //Quand on a traité le dernier quarter on passe à l'année suivante.
//					year++;
//				}
//			}
//		}
//		return quarters_labels;
//	}
//
//	private ArrayList<String> getSemestersLabels(String firstSemester, String lastSemester ) {
//		ArrayList<Const.SEMESTER> semesters = new ArrayList<>();
//		semesters.add(Const.SEMESTER.SEMESTER1);
//		semesters.add(Const.SEMESTER.SEMESTER2);
//
//		ArrayList<String> semesters_labels = new ArrayList<>();
//		int firstSemesterYear = Integer.valueOf(firstSemester.substring(0,3));
//
//		String last_added_semester = ""; //To avoid null pointer exception
//		int year = firstSemesterYear;
//		while (!last_added_semester.equals(lastSemester)) {
//			for (Const.SEMESTER semester : semesters) {
//
//				if (semester.toStringWithYear(year).equals(firstSemester)) {
//					semesters_labels.add(firstSemester);
//					last_added_semester = firstSemester;
//				}
//
//				if (semesters_labels.contains(firstSemester) && !semester.toStringWithYear(year).equals(firstSemester)) { //Tant qu'on a pas ajouté le premier label, on ajoute rien. On commence à ajouter que si le premier label a été trouvé
//					semesters_labels.add(semester.toStringWithYear(year));
//					last_added_semester = semester.toStringWithYear(year);
//				}
//				if (last_added_semester.equals(lastSemester)) {
//					break;
//				}
//				if (semester.equals(Const.SEMESTER.SEMESTER2)) { //Quand on a traité le dernier semester on passe à l'année suivante.
//					year++;
//				}
//			}
//		}
//		return semesters_labels;
//	}
//
//	private ArrayList<String> getSeasonsLabels(String firstSeason, String lastSeason) {
//		ArrayList<Const.S_SEASON> seasons = new ArrayList<>();
//		seasons.add(Const.S_SEASON.S1_WINTER);
//		seasons.add(Const.S_SEASON.S2_SPRING);
//		seasons.add(Const.S_SEASON.S3_SUMMER);
//		seasons.add(Const.S_SEASON.S4_AUTUMN);
//		seasons.add(Const.S_SEASON.S5_WINTER);
//
//		ArrayList<String> seasons_labels = new ArrayList<>();
//		int firstSeasonYear = Integer.valueOf(firstSeason.substring(0,3));
//
//		String last_added_season = ""; //To avoid null pointer exception
//		int year = firstSeasonYear;
//		while (!last_added_season.equals(lastSeason)) {
//			for (Const.S_SEASON season : seasons) {
//
//				if (season.toStringWithYear(year).equals(firstSeason)) {
//					seasons_labels.add(firstSeason);
//					last_added_season = firstSeason;
//				}
//
//				if (seasons_labels.contains(firstSeason) && !season.toStringWithYear(year).equals(firstSeason)) { //Tant qu'on a pas ajouté le premier label, on ajoute rien. On commence à ajouter que si le premier label a été trouvé
//					seasons_labels.add(season.toStringWithYear(year));
//					last_added_season = season.toStringWithYear(year);
//				}
//				if (last_added_season.equals(lastSeason)) {
//					break;
//				}
//				if (season.equals(Const.S_SEASON.S5_WINTER)) { //Quand on a traité le dernier season on passe à l'année suivante.
//					year++;
//				}
//			}
//		}
//		return seasons_labels;
//	}
//}
//
//
