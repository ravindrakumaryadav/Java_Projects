import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FeedbackPanel extends JPanel implements ActionListener
{
	private final String[] feedback = {"Cleaning Issue", "Waiter Bad Behaviour",
			"Bad Management Issue", "Bad Fooding Issue", "Power Cut Issue",
			"Much noise and External Disturbances", "Taking long time to take order", 
			"Others"};
	private HotelRoom[] roomLists;
	private HotelManagementSystem hotelManagementSystem;
	private int currentIndex = 0;
	private JLabel headingLabel = new JLabel("Customer's Feedback");
	private JLabel feedbackLabel = new JLabel("Issue Reported:");
	private JButton submitButton = new JButton("Submit");
	private JButton resetButton = new JButton("Reset");
	private JButton previousButton = new JButton("Previous");
	private JButton nextButton = new JButton("Next");
	private JComboBox feedbackCombo = new JComboBox(feedback);
	private JPanel topPanel = new JPanel();
	private JPanel feedbackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel searchButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel navigateButtonsPanel = new JPanel();
	private HotelRoomDetails feedbackComponents = new HotelRoomDetails();

	
	public FeedbackPanel(HotelManagementSystem hotelSys)
	{
		hotelManagementSystem = hotelSys;
		setLayout(new BorderLayout());
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		previousButton.addActionListener(this);
		nextButton.addActionListener(this);
		resetButton.addActionListener(this);
		submitButton.addActionListener(this);

		feedbackPanel.add(feedbackLabel);
		feedbackPanel.add(feedbackCombo);
		searchButtonsPanel.add(submitButton);
		searchButtonsPanel.add(resetButton);
		navigateButtonsPanel.add(previousButton);
		navigateButtonsPanel.add(nextButton);
		feedbackPanel.setBorder(new javax.swing.border.EmptyBorder(new Insets(0, 5, 0, 0)));
		searchButtonsPanel.setBorder(new javax.swing.border.EmptyBorder(new Insets(0, 5, 0, 0)));

		headingLabel.setAlignmentX(0.5f);

		topPanel.add(Box.createVerticalStrut(10));
		topPanel.add(headingLabel);
		topPanel.add(Box.createVerticalStrut(10));
		topPanel.add(feedbackPanel);
		topPanel.add(searchButtonsPanel);
		topPanel.add(Box.createVerticalStrut(15));
		feedbackComponents.add(navigateButtonsPanel, "Center");
		feedbackComponents.setVisible(false);

		add(topPanel, "North");
		add(feedbackComponents, "Center");
	}

	
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getSource() == submitButton)
			searchButtonClicked();
		else if (ev.getSource() == previousButton)
			previousButtonClicked();
		else if (ev.getSource() == nextButton)
			nextButtonClicked();
		else if (ev.getSource() == resetButton)
			resetButtonClicked();
	}

	
	private void nextButtonClicked()
	{
		if (currentIndex < roomLists.length - 1)
		{
			currentIndex++;
			feedbackComponents.displayDetails(roomLists[currentIndex]);
		}
		else
			JOptionPane.showMessageDialog(hotelManagementSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
	}

	
	private void previousButtonClicked()
	{
		if (currentIndex > 0)
		{
			currentIndex--;
			feedbackComponents.displayDetails(roomLists[currentIndex]);
		}
		else
			JOptionPane.showMessageDialog(hotelManagementSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
	}

	
	private void resetButtonClicked()
	{
		currentIndex = 0;
		roomLists = null;
		feedbackComponents.setVisible(false);
		feedbackCombo.setSelectedIndex(0);
	}

	
	private void searchButtonClicked()
	{
		
		double[] range = HotelManagementSystem.convertToRange((String)feedbackCombo.getSelectedItem());

		if (range[0] >= 0)
		{
			roomLists = hotelManagementSystem.search((int)range[0], (int)range[1]);
		}

		if (roomLists.length > 0)
		{
			currentIndex = 0;
			feedbackComponents.setVisible(true);
			feedbackComponents.displayDetails(roomLists[0]);

			if (roomLists.length == 1)
			{
				nextButton.setEnabled(false);
				previousButton.setEnabled(false);
			}
			else
			{
				nextButton.setEnabled(true);
				previousButton.setEnabled(true);
			}

			hotelManagementSystem.repaint();
		}
		else
			JOptionPane.showMessageDialog(hotelManagementSystem, "Sorry, no search results were returned", "Search failed", JOptionPane.WARNING_MESSAGE);
	}
}