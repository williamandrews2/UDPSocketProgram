import java.net.*;
import java.io.*;
import java.util.Arrays;

public class WAndrewsUDPServer 
{

	public static void main(String[] args) 
	{
		
		try 
		{
			// Creates a datagram socket and binds it to port 10999
			DatagramSocket serverSocket = new DatagramSocket(10999); 

			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			
			while (true) 
			{
				// Status messsage printed by server when it is started.
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				
				// Receive data in packet
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				
				// Retrieve IP Address and port number
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				
				// String stores the recieved data from the client.
				String receivedSentence = new String(receivePacket.getData());
				System.out.println(receivedSentence);
				// Send the data back to the client side
				sendData = receivedSentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);

				Arrays.fill(receiveData, (byte)0); 
				Arrays.fill(sendData, (byte)0);	
				System.out.println();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
