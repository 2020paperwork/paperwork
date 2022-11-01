package test;
import static edu.monash.crypto.hve.SHVE.*;
import edu.monash.crypto.hve.param.KeyParameter;
import edu.monash.crypto.hve.param.impl.SHVEMasterSecretKeyParameter;
import edu.monash.crypto.hve.param.impl.SHVESecretKeyParameter;
import edu.monash.crypto.hve.util.AESUtil;
import java.util.List;
import test.Graycode;

public class Test {
    public static void main(String[] args) {

        Graycode test=new Graycode(2,1);//设定成16个格子,长度取1 x和y的坐标取[0,4]
        long start,end;
        int []vector_1=test.transform(0.5,0.7);
        int []vector_2=test.transform(1.2,0.63);
        int []vector_3=test.transform(3.4,1.8);
        int []vector_4=test.transform(3.2,2.4);

        System.out.print("vector_1:");
        for (int element: vector_1) {
            System.out.print(element);
        }
        System.out.print("\n");
        System.out.print("vector_2:");
        for (int element: vector_2) {
            System.out.print(element);
        }
        System.out.print("\n");
        System.out.print("vector_3:");
        for (int element: vector_3) {
            System.out.print(element);
        }
        System.out.print("\n");
        System.out.print("vector_4:");
        for (int element: vector_4) {
            System.out.print(element);
        }
        System.out.print("\n");

        int n=vector_1.length;
        KeyParameter MSK = setup(n);//密钥产生

        System.out.println("下面进行加密并记录加密时间");
        start = System.nanoTime();//将全部的数据进行加密
        List<byte[]> c_1=enc(MSK,vector_1);
        List<byte[]> c_2=enc(MSK,vector_2);
        List<byte[]> c_3=enc(MSK,vector_3);
        List<byte[]> c_4=enc(MSK,vector_4);
        end = System.nanoTime();
        System.out.println("Enc Time: "+(end-start));

        //假设我们选择vector_1和vector_2作为查询的范围
        int [][] temp=new int[2][vector_1.length];
        temp[0]=vector_1;
        temp[1]=vector_2;

        System.out.println("选取vector_1和vector_2作为查询范围，查询的范围未化简前:");
        for (int i=0;i<temp.length;i++)//输出未化简前的temp
        {
            for(int j=0;j<temp[i].length;j++)
            {
                System.out.print(temp[i][j]+" ");
            }
            System.out.print("\n");
        }

        temp= test.syntheic(temp);

        System.out.println("查询的范围化简后:");
        for (int i=0;i<temp.length;i++)////输出化简后的temp
        {
            for(int j=0;j<temp[i].length;j++)
            {
                System.out.print(temp[i][j]+" ");
            }
            System.out.print("\n");
        }

        System.out.println("下面产生token并记录运行的时间");
        start = System.nanoTime();
        SHVESecretKeyParameter sk = (SHVESecretKeyParameter) keyGen(MSK,temp[0]);
        end = System.nanoTime();
        System.out.println("KeyGen Time: " + (end - start));

        start = System.nanoTime();
        System.out.println("Query Result of vector_1: " + evaluate(sk,c_1));
        System.out.println("Query Result of vector_2: " + evaluate(sk,c_2));
        System.out.println("Query Result of vector_3: " + evaluate(sk,c_3));
        System.out.println("Query Result of vector_4: " + evaluate(sk,c_4));
        end = System.nanoTime();
        System.out.println("Query Time: " + (end - start));
    }
}
