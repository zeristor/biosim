package biosim.client.environment.gui;

import java.awt.*;
import biosim.idl.environment.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the Environment
 *
 * @author    Scott Bell
 */
public class EnvironmentPieChartPanel extends GraphPanel
{
	private SimEnvironment mySimEnvironment;
	private Pie3DPlot myPlot;
	private JFreeChart myChart;
	private DefaultPieDataset myDataset;
	private String O2Category = "O2";
	private String CO2Category = "CO2";
	private String otherCategory = "Other";

	protected void createGraph(){
		setDelay(10000);
		// create the chart...
		mySimEnvironment = (SimEnvironment)(BioHolder.getBioModule(BioHolder.simEnvironmentName));
		refresh();
		myChart = ChartFactory.createPie3DChart(
		                  "Environment Gas Composition",  // chart title
		                  myDataset,                 // data
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = (Pie3DPlot)(myChart.getPlot());
		myPlot.setDepthFactor(2.0d);
		myPlot.setSeriesPaint(new Paint[] { Color.BLUE, Color.GREEN, Color.RED});
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle(0));
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(12.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(250, 250));
	}

	public void refresh() {
		if (myDataset == null)
			myDataset = new DefaultPieDataset();
		myDataset.setValue(O2Category, new Float(mySimEnvironment.getO2Level()));
		myDataset.setValue(CO2Category, new Float(mySimEnvironment.getCO2Level()));
		myDataset.setValue(otherCategory, new Float(mySimEnvironment.getOtherLevel()));
	}
}
