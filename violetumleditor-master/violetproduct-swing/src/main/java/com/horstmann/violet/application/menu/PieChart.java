package com.horstmann.violet.application.menu;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PieChart {
    
    private String objectNameList[];
    private int outMessageList[];
    private int numberObjectsList = 0;
      
    PieChart()
    {
        readFile("Stats.txt");
        createPieChart();
    }
    
    public String[] getObjectNameList() 
    {
		return objectNameList;
	}

	public void setObjectNameList(String[] objectNameList) 
	{
		this.objectNameList = objectNameList;
	}

	public int[] getOutMessageList() 
	{
		return outMessageList;
	}

	public void setOutMessageList(int[] outMessageList) 
	{
		this.outMessageList = outMessageList;
	}

	public int getNumberObjectsList() 
	{
		return numberObjectsList;
	}

	public void setNumberObjectsList(int numberObjectsList) 
	{
		this.numberObjectsList = numberObjectsList;
	}

	public void readFile(String inFile)
    {
        BufferedReader br = null;
        
        List<String> objectNames = new ArrayList<String>();
        List<Integer> outMessageNumber = new ArrayList<Integer>();
        
        try 
        {
            //File from sequence Diagram containing information
            br = new BufferedReader(new FileReader(inFile));
            String str;
            
            //read lines and put inforamtion into 3 different Lists
            while((str = br.readLine()) != null)
            {
                String tempName = str.substring(0, str.indexOf(":"));
                objectNames.add(tempName);
                
                String out = str.substring(str.indexOf(":"), str.indexOf(","));
                String tempOut = out.substring(1);
                outMessageNumber.add(Integer.parseInt(tempOut));
                
            }
            
            //Convert lists to arrays
            objectNameList = objectNames.toArray(new String[0]);
            outMessageList = toIntArray(outMessageNumber);
            
            numberObjectsList = objectNameList.length;
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                br.close();
            }
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    //Used to create int array
    int[] toIntArray(List<Integer> list)
    {
        int[] intArr = new int[list.size()];
        for(int i = 0;i < intArr.length;i++){
            intArr[i] = list.get(i);
        }
        return intArr;
    }
    
    public void createPieChart() 
    {
        // create a dataset...
        DefaultPieDataset data = new DefaultPieDataset();
        for(int i = 0; i<getNumberObjectsList() ; i++)
        {
            data.setValue(objectNameList[i], outMessageList[i]);
        }
        // create a chart...
        JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart",
                data,
                true,    // legend?
                true,    // tooltips?
                false    // URLs?
        );
        // create and display a frame...
        ChartFrame frame = new ChartFrame("Statistics", chart);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }
}
