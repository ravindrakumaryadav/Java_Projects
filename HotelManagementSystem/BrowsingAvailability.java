import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BrowsingAvailability extends JPanel implements ActionListener
{
	private final String[] price = {"50-500 Rs.", "501-1000 Rs.", "1001-1500 Rs.", "1501-2000 Rs."};
	private final String[] distance = {"0-1 Hour", "1-2 Hour", "3-5 Hour", "6-10 Hour","10-15 Hour", "16-24 Hour", "25-48 Hour", "49-72 Hour"};
	private HotelRoom[] carList;
	private HotelManagementSystem carSystem;
	private int currentIndex = 0;
	private JLabel headingLabel = new JLabel("Search Room Availability");
	private JLabel priceLabel = new JLabel("Search by Price:");
	private JLabel distanceLabel = new JLabel("Required for(Hour):");
	private JButton searchButton = new JButton("Search");
	private JButton resetButton = new JButton("Reset");
	private JButton previousButton = new JButton("Previous");
	private JButton nextButton = new JButton("Next");
	private JComboBox priceCombo = new JComboBox(price);
	private JComboBox distanceCombo = new JComboBox(distance);
	private JPanel topPanel = new JPanel();
	private JPanel pricePanel = new JPanel();
	private JPanel distancePanel = new JPanel();
	private JPanel priceDistancePanel = new JPanel();
	private JPanel searchButtonsPanel = new JPanel();
	private JPanel navigateButtonsPanel = new JPanel();
	private HotelRoomDetails carComponents = new HotelRoomDetails();

	
	public BrowsingAvailability(HotelManagementSystem carSys)
	{
		carSystem = carSys;
		setLayout(new BorderLayout());
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		previousButton.addActionListener(this);
		nextButton.addActionListener(this);
		resetButton.addActionListener(this);
		searchButton.addActionListener(this);

		pricePanel.add(priceLabel);
		pricePanel.add(priceCombo);
		distancePanel.add(distanceLabel);
		distancePanel.add(distanceCombo);
		priceDistancePanel.add(pricePanel);
		priceDistancePanel.add(distancePanel);

		searchButtonsPanel.add(searchButton);
		searchButtonsPanel.add(resetButton);
		navigateButtonsPanel.add(previousButton);
		navigateButtonsPanel.add(nextButton);

		headingLabel.setAlignmentX(0.5f);
		topPanel.add(Box.createVerticalStrut(10));
		topPanel.add(headingLabel);
		topPanel.add(Box.createVerticalStrut(10));
		topPanel.add(priceDistancePanel);
		topPanel.add(searchButtonsPanel);
		topPanel.add(Box.createVerticalStrut(15));
		carComponents.add(navigateButtonsPanel, "Center");
		carComponents.setVisible(false);

		add(topPanel, "North");
		add(carComponents, "Center");
	}

	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getSource() == searchButton)
			searchButtonClicked();
		else if (ev.getSource() == resetButton)
			resetButtonClicked();
		else if (ev.getSource() == previousButton)
			previousButtonClicked();
		else if (ev.getSource() == nextButton)
			nextButtonClicked();
	}

	
	private void nextButtonClicked()
	{
		if (currentIndex < carList.length - 1)
		{
			currentIndex++;
			carComponents.displayDetails(carList[currentIndex]);
		}
		else
			JOptionPane.showMessageDialog(carSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
	}

	
	private void previousButtonClicked()
	{
		if (currentIndex > 0)
		{
			currentIndex--;
			carComponents.displayDetails(carList[currentIndex]);
		}
		else
			JOptionPane.showMessageDialog(carSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
	}

	
	private void resetButtonClicked()
	{
		currentIndex = 0;
		carList = null;
		carComponents.setVisible(false);
		priceCombo.setSelectedIndex(0);
		distanceCombo.setSelectedIndex(0);
	}

	
	private void searchButtonClicked()
	{
		
		double[] distanceRange = HotelManagementSystem.convertToRange((String)distanceCombo.getSelectedItem());
		double[] priceRange = HotelManagementSystem.convertToRange((String)priceCombo.getSelectedItem());

		if (priceRange[0] >= 0 && distanceRange[0] >= 0)
		{
			carList = carSystem.search((int)priceRange[0], (int)priceRange[1], (double)distanceRange[0], (double)distanceRange[1]);
		}

		if (carList.length > 0)
		{
			currentIndex = 0;
			carComponents.setVisible(true);
			carComponents.displayDetails(carList[0]);

			if (carList.length == 1)
			{
				nextButton.setEnabled(false);
				previousButton.setEnabled(false);
			}
			else
			{
				nextButton.setEnabled(true);
				previousButton.setEnabled(true);
			}

			carSystem.repaint();
		}
		else
			JOptionPane.showMessageDialog(carSystem, "Sorry, no search results were returned", "Search failed", JOptionPane.WARNING_MESSAGE);
	}
}