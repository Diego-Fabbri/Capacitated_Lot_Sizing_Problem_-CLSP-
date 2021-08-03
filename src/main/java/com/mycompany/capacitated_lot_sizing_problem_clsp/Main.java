
package com.mycompany.capacitated_lot_sizing_problem_clsp;

import Utility.Data;
import ilog.concert.IloException;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Main {
    public static void main(String[] args) throws IloException, FileNotFoundException{
     System.setOut(new PrintStream("Capacited_Lot_Sizing.log"));
    int numPeriods = Data.numPeriods();
    int Io = Data.Stat_Inventory_Level();
    int In = Data.End_Inventory_Level();
    double [] Unit_costs =Data.Unit_Costs();
    double [] demands = Data.Demands();
    double [] Fixed_costs = Data.Fixed_Costs();
    double [] Inventory_costs = Data.Inventory_Costs();
    double [] capacities = Data.Capacities();
    CLSP_Model model = new CLSP_Model (numPeriods,Io,In,Unit_costs,demands,Fixed_costs,Inventory_costs,capacities);
   model.solveModel();
}
}