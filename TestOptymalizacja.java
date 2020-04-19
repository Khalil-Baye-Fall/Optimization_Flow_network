package com.optymalizacja.main;
import ilog.concert.*;
import ilog.cplex.*;



public class TestOptymalizacja {

	public static void main(String[] args) throws IloException {

		double[] capacity = { 200, 500, 700, 400, 1000, 500, 300, 700, 800, 500, 600, 1200, 1000, 900, 500, 600, 700,
				300, 550, 700, 1400, 900, 1500, 1000, 400, 600, 300, 900 };// all capacity 28
		double[] routes = { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0,
				1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0 };
		// all ways if 1(accepted) or 0 (not accepted)
		double[] transmission_max = { 800, 900, 500, 800, 700, 400, 700, 900, 1000, 1050, 350, 450, 800, 380, 900,
				870 };// it's maximum transmission....
		double[] transmission_min = { 100, 200, 400, 500, 200, 300, 600, 100, 250, 540, 390, 400, 250, 470, 300, 270,
				180 };// it's Minimum transmission
		double[] X = { 3.45, 2.50, 4.20, 2.1, 1.5, 3.2, 4.7, 1.3, 3.12, 4.15, 3.10, 2.50, 1.50, 3.76, 2.87, 4.46,
				2.30 };

		double[] A1 = {0,1,2,3,4,5};
		int[] A2 = {0,2,3,4,5};
		int i;

		// Instantiate an empty model
		IloCplex flow = new IloCplex();

		IloNumVar[] cp = new IloNumVar[capacity.length];
		for (i = 0; i < capacity.length; i++) {
			// Define each variable's range from 0 to +Infinity
			cp[i] = flow.numVar(0, Double.MAX_VALUE);
		}

		IloNumVar[] rt = new IloNumVar[routes.length];
		for (i = 0; i < routes.length; i++) {
			// Define each variable's range from 0 to +Infinity
			rt[i] = flow.numVar(0, Double.MAX_VALUE);
		}

		IloNumVar[] tmax = new IloNumVar[transmission_max.length];
		for (i = 0; i < transmission_max.length; i++) {
			// Define each variable's range from 0 to +Infinity
			tmax[i] = flow.numVar(0, Double.MAX_VALUE);
		}

		IloNumVar[] tmin = new IloNumVar[transmission_min.length];
		for (i = 0; i < transmission_min.length; i++) {
			// Define each variable's range from 0 to +Infinity
			tmin[i] = flow.numVar(0, Double.MAX_VALUE);
		}

		IloNumVar[] x = new IloNumVar[X.length];
		for (i = 0; i < transmission_min.length; i++) {
			// Define each variable's range from 0 to +Infinity
			x[i] = flow.numVar(0, Double.MAX_VALUE);
		}
		IloNumVar[] a1 = new IloNumVar[A1.length];
		for (i = 0; i < A1.length; i++) {
			// Define each variable's range from 0 to +Infinity
			a1[i] = flow.numVar(0, Double.MAX_VALUE);
		}
		IloNumVar[] a2 = new IloNumVar[A2.length];
		for (i = 0; i < A2.length; i++) {
			// Define each variable's range from 0 to +Infinity
			a2[i] = flow.numVar(0, Double.MAX_VALUE);
		}
		
		
		// the goal of maximization.....
		
		/*flow.sum(
				flow.prod(flow.prod(x[i], Math.pow((1 - a1), -1)),
						flow.prod(Math.pow(transmission_max[i], (1 - a1)), null)),
				flow.prod(-1, null), flow.prod(rt[i], a2)

		);*/
		
		IloLinearNumExpr profit = flow.linearNumExpr();
		

		// All constraints.....
		for (i = 0; i < routes.length; i++) {
			while (routes[i] == 1) {

				flow.addLe(tmax[i], cp[i]);

				flow.addGe(tmax[i], tmin[i]);

			}
		}

		try {

			boolean is_solved = flow.solve();
			System.out.println(is_solved);
		}catch(IloException ex) {
			System.err.println("Our problem is not solved!!");
		}

	}

	

}
