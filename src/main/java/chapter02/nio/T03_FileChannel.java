package chapter02.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/**
 * Java NIO FileChannel是连接文件的通道。使用FileChannel，您可以从文件中读取数据和将数据写入文件。
 * Java NIO FileChannel类是NIO用于替代使用标准Java IO API读取文件的方法。
 * FileChannel无法设置为非阻塞模式。它总是以阻止模式运行
 * 使用之前，FileChannel必须被打开，但是你无法直接打开FileChannel。
 * 需要通过InputStream，OutputStream或RandomAccessFile获取FileChannel
 */
public class T03_FileChannel 
{
	@Test
	public void read() throws Exception
	{
		RandomAccessFile  file = new RandomAccessFile ("D:\\S456789.txt","rw");
		FileChannel fc = file.getChannel();
		
		ByteBuffer dst = ByteBuffer.allocate(32);
		//异步操作
		int read = fc.read(dst) ;
		
		while( read != -1 )
		{
			dst.flip();
			while(dst.hasRemaining())
			{
				System.out.print((char)dst.get());
			}
			dst.clear();
			read = fc.read(dst);
		}
		
		//关闭
		fc.close();
		file.close();
	}
	
}
