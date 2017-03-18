package com.convergent.struts2.actions;

import user.oauth.data.broker.WordsPointsBroker;

import com.opensymphony.xwork2.ActionSupport;


public class DocumentCategorizationAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String input = "";
	private String category = "";
	private double points = 0.0;
	
	
	public String getInput() {
		return this.input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	public String categorizationPage() {
		return ActionSupport.SUCCESS;
	}

	public String categorizationResult() {
		WordsPointsBroker broker = new WordsPointsBroker();
		String result = broker.pointsCalculation(input);
		String[] array = result.split("\\s+");
		setCategory(array[0]);
		setPoints(Double.parseDouble(array[1]));
		return ActionSupport.SUCCESS;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	
}
