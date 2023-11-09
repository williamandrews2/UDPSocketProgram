
import java.net.*;
import java.io.*;
import static java.lang.System.*;

public class WAndrewsUDPClient 
{

	public static void main(String[] args) 
	{
		String serverName = "localhost";
		int port = 10999;

		// Prompt the user 
		String sentence = String.join(" ", args);
		
		try 
		{	
			// Send the data to the server
			DatagramSocket clientSocket = new DatagramSocket();		

			// Get IP Address
			InetAddress IPAddress = InetAddress.getByName(serverName); 

			// Sending byte array
			byte[] sendData = new byte[1024];			

			// Fill array with the data to send in bytes
			sendData = sentence.getBytes();

			// Receive the data from the server.
			byte[] receiveData = new byte[1024]; // Receiving byte array

			// Send the packet to the server
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); // 4 parameters
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // Only need 2 parameters.

			clientSocket.send(sendPacket);
            long startTime = System.nanoTime();		
			clientSocket.receive(receivePacket);
			long endTime = System.nanoTime();

			String receivedSentence = new String(receivePacket.getData());

			// Trim whitespace on the strings
			sentence = sentence.trim();
			receivedSentence = receivedSentence.trim();

			// Find length of sentence in bits, round trip time, and the throughput
			long sentenceSize = sentence.getBytes().length * 8;
			double RTT = (endTime - startTime) / (Math.pow(10, 6));
			double throughput = ((sentenceSize / (RTT * 0.5))*1000) / (Math.pow(10, 6));

			// Check if the message received is the same as the one that was sent
			if(sentence.equals(receivedSentence)) {
				
				// Print out the length, RTT, and throughput
				out.println("================ File Information ===============");
				out.println("Successfully sent message.");
				out.println("Length of sentence: " + sentenceSize + " bits");
				out.printf("RTT: %.2f ms%n", RTT);
				out.printf("Throughput: %.1f Mbps", throughput);
				
			}
			else {
				out.println("ERROR!");
			}
			
			clientSocket.close();
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 

	} 

}
