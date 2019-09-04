package chapter02.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class T03_FileChannelCopy {

	private static FileInputStream input;
	private static FileOutputStream output;

	public static void main(String[] args) throws Exception 
	{
			input = new FileInputStream("/Users/thirteenliu/Project/JavaSourceCode/JavaLanguageSpecification/src/main/java/base04/io/nio/input.txt");
			output = new FileOutputStream("/Users/thirteenliu/Project/JavaSourceCode/JavaLanguageSpecification/src/main/java/base04/io/nio/output.txt");
			
			FileChannel in = input.getChannel();
			FileChannel out = output.getChannel();
			
			ByteBuffer buf = ByteBuffer.allocate(5);
			
			while(true)
			{
				
				int read = in.read(buf);
				buf.flip();
				out.write(buf);
				System.out.println(read);
				if(read == -1)
				{
					break;
				}
				else
				{
					buf.clear();
				}
			}
		
	}

}
