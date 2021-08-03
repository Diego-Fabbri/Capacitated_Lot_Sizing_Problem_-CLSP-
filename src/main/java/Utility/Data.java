
package Utility;


public class Data {
    public static int numPeriods() {// Period t=0 NOT TAKEN INTO ACCOUNT
        int numPeriods = 6;
        return numPeriods;
    }
    public static int Stat_Inventory_Level() {
        int Stat_Inventory_Level = 0;
        return Stat_Inventory_Level;
    }
     public static int End_Inventory_Level() {
        int End_Inventory_Level = 0;
        return End_Inventory_Level;
    }
      public static double[] Demands() {
        double[] Demands = {60,100,140,200,120,80};
        return Demands;
    }
    public static double[] Fixed_Costs() {
        double[] Fixed_Costs = {180,140,160,160,170,190};
        return Fixed_Costs;
    }
    
   public static double[] Unit_Costs() {
        double[] Unit_Costs = {7,7,8,7,6,10};
        return Unit_Costs;
    }
    public static double[] Inventory_Costs() {
        double[] Inventory_Costs = {1,1,2,2,2,2};
        return Inventory_Costs;
    }
     public static double[] Capacities() {
        double[] Capacities=
      {Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE};
        return Capacities;
    }
}
