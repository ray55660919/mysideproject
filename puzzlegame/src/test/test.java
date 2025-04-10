package test;

import java.util.Random;

public class test {
    public static void main(String[] args) {
        //打亂一維數組
        int [] arr={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random rand = new Random();
        for(int n=0;n<arr.length;n++){
            int index = rand.nextInt(arr.length);
            int temp = arr[n];
            arr[n] = arr[index];
            arr[index] = temp;
        }
        for(Integer num:arr){
            System.out.print(num+" ");
        }
        System.out.println();
        //將打亂的一維數組放到二維數組
        int index=0;
        int [][]arr2=new int[4][4];
        for(int i=0;i<arr2.length;i++){
            for(int j=0;j<arr2[i].length;j++){
                arr2[i][j]=arr[index];
                index++;
            }
        }
        for(int i=0;i<arr2.length;i++){
            for(int j=0;j<arr2[i].length;j++){
                System.out.print(arr2[i][j]+" ");
            }
            System.out.println();
        }
    }
}
