import java.util.*;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

public class RearrangementForm extends JPanel implements ActionListener
{
	private HotelManagementSystem hotelSystem;
	private JLabel headingLabel = new JLabel("Add room's availability");
	private JButton resetButton = new JButton("Reset");
	private JButton saveButton = new JButton("Save");
	private JPanel buttonPanel = new JPanel();
	private HotelRoomDetails carComponents = new HotelRoomDetails();

	public RearrangementForm(HotelManagementSystem hotelSys)
	{
		hotelSystem = hotelSys;

		resetButton.addActionListener(this);
		saveButton.addActionListener(this);
		headingLabel.setAlignmentX(0.5f);

		buttonPanel.add(resetButton);
		buttonPanel.add(saveButton);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(Box.createVerticalStrut(10));
		add(headingLabel);
		add(Box.createVerticalStrut(15));
		carComponents.add(buttonPanel, "Center");
		add(carComponents);
	}


	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getSource() == resetButton)
			resetButtonClicked();
		else if (ev.getSource() == saveButton)
			saveButtonClicked();
	}

	private void resetButtonClicked()
	{
		carComponents.clearTextFields();
	}

	private void saveButtonClicked()
	{
		String numberOfRooms = "";
		String singleBedRooms = "";
		String doubleBedRooms = "";
		String bookedRoom = "";
		int price = 0;
		int year = 0;
		boolean valid = false;
		try
		{
			numberOfRooms = carComponents.getManufacturerText().trim();
			singleBedRooms = carComponents.getModelText().trim();
			doubleBedRooms = carComponents.getInfoText().trim();
			bookedRoom = carComponents.getKmText().trim();
			price = Integer.parseInt(carComponents.getPriceText().trim());
			year = Integer.parseInt(carComponents.getYearText().trim());

			
			if (validateString(numberOfRooms))
			{
				if (year >= 1000 && year <= 9999)
				{
					if (validateString(singleBedRooms))
					{
						if (validateKilometers(carComponents.getKmText().trim()))
						{
							valid = true;
						}
						else
							JOptionPane.showMessageDialog(hotelSystem, "An error has occured due to incorrect \"Km Traveled\" text field data.\nThis text field must contain a number with one decimal place only.", "Invalid field", JOptionPane.ERROR_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(hotelSystem, "An error has occured due to incorrect \"Model\" text field data.\nThis text field must contain any string of at least two non-spaced characters.", "Invalid field", JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(hotelSystem, "An error has occured due to incorrect \"Year\" text field data.\nThis text field must be in the form, YYYY. ie, 2007.", "Invalid field", JOptionPane.ERROR_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(hotelSystem, "An error has occured due to incorrect \"Manufacturer\" text field data.\nThis text field must contain any string of at least two non-spaced characters.", "Invalid field", JOptionPane.ERROR_MESSAGE);

		}
		catch (NumberFormatException exp)
		{
			JOptionPane.showMessageDialog(hotelSystem, "An unknown error has occured. Please ensure your fields meet the following requirements:\n" +
			"The \"Year\" field must contain four numeric digits only\nThe \"Price\" field must contain a valid integer with no decimal places\nThe \"Km Traveled\" field must contain a number which can have a maximum of one decimal place", "Invalid field", JOptionPane.ERROR_MESSAGE);
		}

		if (valid)
		{
			
			HotelRoom myRoom = new HotelRoom(numberOfRooms, singleBedRooms, doubleBedRooms);
			myRoom.setSetBookedRoom(bookedRoom);
			myRoom.setPrice(price);
			myRoom.setYear(year);

			
			int result = hotelSystem.addNewRoom(myRoom);

			if (result == RoomCollection.NO_ERROR)
			{
				hotelSystem.setRoomsUpdated();
				JOptionPane.showMessageDialog(hotelSystem, "Record added.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				resetButtonClicked();
				carComponents.setFocusManufacturerTextField();
			}
			else if (result == RoomCollection.CARS_MAXIMUM_REACHED)
				JOptionPane.showMessageDialog(hotelSystem, "The number of room for that Hotel has been reached to the maximum.\nUnfortunately you cannot add any further room to this this hotel", "Problem adding room", JOptionPane.WARNING_MESSAGE);
			else if (result == RoomCollection.MANUFACTURERS_MAXIMUM_REACHED)
				JOptionPane.showMessageDialog(hotelSystem, "The number of room in the that hotel has been reached to the maximum.\nUnfortunately you cannot add any further room to this Hotel", "Problem adding room", JOptionPane.WARNING_MESSAGE);
		}
	}

	private boolean validateString(String arg)
	{
		boolean valid = false;
		String[] splitted = arg.split(" "); 

		for (int i = 0; i < splitted.length; i++)
		{
			// checks if the number of characters between a space is greater than 2
			valid = (splitted[i].length() > 2);
			if (valid)
				break;
		}

		return valid;
	}

	private boolean validateKilometers(String distance)
	{
		boolean valid = false;
		String rem;
		StringTokenizer tokens = new StringTokenizer(distance, ".");

		tokens.nextToken();

		if (tokens.hasMoreTokens())
		{
			
			rem = tokens.nextToken();
			
			if (rem.length() == 1)
				valid = true;
			else
			{
				if ((Integer.parseInt(rem)) % (Math.pow(10, rem.length() - 1)) == 0)
					valid = true;
				else
					valid=false;
			}
		}
		else 
			valid = true;

		return valid;
	}
}