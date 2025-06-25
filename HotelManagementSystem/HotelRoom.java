import java.util.*;

public class HotelRoom implements java.io.Serializable
{
	private String numberOfRooms;
	private String singleBedRooms;
	private String doubleBedRooms;
	private int year;
	private int price;
	private String bookedRoom;

	public HotelRoom(){}

	public HotelRoom(String single, String rooms,String doub)
	{
		numberOfRooms = rooms;
		singleBedRooms = single.toUpperCase();
		doubleBedRooms = doub;
	}

	public int getAge()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		return (calendar.get(Calendar.YEAR) - year);
	}

	public String getInformation()
	{
		return doubleBedRooms;
	}

	public String getBookedRoom()
	{
		return bookedRoom;
	}

	public String getManufacturer()
	{
		return singleBedRooms;
	}

	public String getModel()
	{
		return numberOfRooms;
	}

	public int getPrice()
	{
		return price;
	}

	public int getYear()
	{
		return year;
	}

	public void setInformation(String info)
	{
		doubleBedRooms = info;
	}

	public void setSetBookedRoom(String booked)
	{
		bookedRoom = booked;
	}
	

	public void setManufacturer(String man)
	{
		singleBedRooms = man.toUpperCase();
	}

	public void setModel(String no)
	{
		numberOfRooms = no;
	}

	public void setPrice(int cost)
	{
		price = cost;
	}

	public void setYear(int yr)
	{
		year = yr;
	}
}