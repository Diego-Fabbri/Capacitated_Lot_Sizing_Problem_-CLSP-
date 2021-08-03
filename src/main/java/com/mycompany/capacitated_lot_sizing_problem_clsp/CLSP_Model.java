

package com.mycompany.capacitated_lot_sizing_problem_clsp;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVarType;
import ilog.concert.IloObjective;
import ilog.concert.IloObjectiveSense;
import ilog.cplex.IloCplex;

public class CLSP_Model {

    protected IloCplex model;

    protected int T;
    protected int Io;
    protected int In;
    protected double[] unit_costs;
    protected double[] demands;
    protected double[] fixed_costs;
    protected double[] inventory_costs;
    protected double[] capacities;

    protected IloIntVar[] q;
    protected IloIntVar[] I;
    protected IloIntVar[] y;

    CLSP_Model(int numPeriods, int Io, int In, double[] Unit_costs, double[] demands, double[] Fixed_costs, double[] Inventory_costs, double[] capacities) throws IloException {
        this.model = new IloCplex();
        this.Io = Io;
        this.In = In;
        this.T = numPeriods;
        this.unit_costs = Unit_costs;
        this.demands = demands;
        this.fixed_costs = Fixed_costs;
        this.inventory_costs = Inventory_costs;
        this.capacities = capacities;
        this.y = new IloIntVar[T + 1];// there is also a NON significant variable y_o
        this.q = new IloIntVar[T + 1];// there is also a NON significant variable q_o
        this.I = new IloIntVar[T + 1];
    }

    //The following code creates the variables
    protected void addVariables() throws IloException {

        for (int t = 0; t <= T; t++) {

            q[t] = (IloIntVar) model.numVar(0, Float.MAX_VALUE, IloNumVarType.Float, "q[" + t + "]");

        }
        for (int t = 0; t <=T; t++) {

            I[t] = (IloIntVar) model.numVar(0, Float.MAX_VALUE, IloNumVarType.Float, "I[" + t + "]");

        }
        for (int t = 0; t <= T; t++) {

            y[t] = (IloIntVar) model.numVar(0, 1, IloNumVarType.Int, "y[" + t + "]");

        }
    }
    
    //The following code creates the objective function for the problem.
    protected void addObjective() throws IloException {
        IloLinearNumExpr objective = model.linearNumExpr();

       
            for (int t = 1; t <=T; t++) {
                objective.addTerm(y[t], fixed_costs[t-1]);
                objective.addTerm(q[t], unit_costs[t-1]);
                objective.addTerm(I[t], inventory_costs[t-1]);
    }

        IloObjective Obj = model.addObjective(IloObjectiveSense.Minimize, objective);
    }
    
    
    
    
    //The following code defines the constraints for the problem

    protected void addConstraints() throws IloException {
// BALANCE DEMAND q_t + I_{t-1} - I_t = d_t
        for (int t = 1; t <= T; t++) {
            IloLinearNumExpr expr_1 = model.linearNumExpr();

            expr_1.addTerm(q[t], 1);
            expr_1.addTerm(I[t - 1], 1);
            expr_1.addTerm(I[t], - 1);
            model.addEq(expr_1, demands[t-1]);

        }
        // Initial and Final Inventory Level
        model.addEq(I[0],Io);
        model.addEq(I[T],In);
        // Non significant variables
        model.addEq(y[0],0);
        model.addEq(q[0],0);
        
        
        // CAPACITY q_t - C_t*y_t <=0
        for (int t = 1; t <= T; t++) {
            IloLinearNumExpr expr_2 = model.linearNumExpr();

            expr_2.addTerm(q[t], 1);
            expr_2.addTerm(-capacities[t],y[t]);
            
            model.addLe(expr_2, 0);

        }

    }
public void solveModel() throws IloException {
        addVariables();
        addObjective();
        addConstraints();
        model.exportModel("Capacited_Lot_Sizing.lp");

        model.solve();

        if (model.getStatus() == IloCplex.Status.Feasible
                | model.getStatus() == IloCplex.Status.Optimal) {
            System.out.println();
            System.out.println("Solution status = " + model.getStatus());
            System.out.println();
            System.out.println("Total Cost " + model.getObjValue());
            
            for (int t = 1; t <= T; t++) {
            System.out.println("Time t = "+ t );
            System.out.println( "---> "+ q[t].getName() +" = "+ model.getValue(q[t]));
            System.out.println( "---> "+ I[t].getName() +" = "+ model.getValue(I[t]));
            System.out.println( "---> "+ y[t].getName() +" = "+ model.getValue(y[t]));
            }
            
        } else {
            System.out.println("The problem status is: " + model.getStatus());
        }
    }

}

    
    
    
    

