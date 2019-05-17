/* IMPORTANT: Multiple classes and nested static classes are supported */

/*
 * uncomment this if you want to read input.
//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility classes
*/
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail

class TestClass4 {
    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;
 
        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public String readLine() throws IOException
        {
            byte[] buf = new byte[500]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n' && cnt > 0){
                    break;
                }
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }
 
        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');
 
            if (neg)
                return -ret;
            return ret;
        }
 
        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }
 
        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
 
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
 
            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }
 
            if (neg)
                return -ret;
            return ret;
        }
 
        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }
 
        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }
 
        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
    
  
    public static void main(String args[] ) throws Exception {

    	Reader s=new Reader();
        BufferedOutputStream out=new BufferedOutputStream(System.out);
		StringBuilder sb=new StringBuilder();
       
        int tests = s.nextInt();
       
        int length = 0;
        String string = "";
        
        int [] allPrimes = {2,3,5,7,11,13,
        		17,19,23,29,31,37,
        		41,43,47,53,59,61,
        		67,71,73,79,83,89,
        		97,101,103,107,109,
        		113,127};
        
        int [] primes = {67,71,73,79,83,89,
        		97,101,103,107,109,113};
        
        for(int i = 0; i < tests; i++){
        	length = s.nextInt();
        	string = s.readLine().trim();
        	for(int j = 0; j < length; j++){
        		int symb = string.charAt(j);
        		int resSymb = 0;
        		boolean found = false;
        		for(int k = 0; k < primes.length; k++){
        			if(primes[k] > symb){
        				if(k > 0){
        					int l1 = symb - primes[k-1];
        					int l2 = primes[k] - symb;
        					if(l1 > l2){
        						resSymb = primes[k];
        					}
        					else{
        						resSymb = primes[k-1];
        					}
        					found = true;
        				}
        				else{
        					resSymb = primes[k];
        				}
        			}
        			if(found){
        				break;
        			}
        		}
        		
        		if(!found){
        			resSymb = primes[primes.length-1];
        		}
        		
        		sb.append((char)resSymb);
        	}
        	sb.append("\n");
        }
        
        
        
        
        out.write(sb.toString().getBytes());
		out.flush();
    }

}
