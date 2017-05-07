import java.io.*;
import java.util.*;

class Student implements Comparable<Student> {
   private int token;
   private String fname;
   private double cgpa;
   public Student(int id, String fname, double cgpa) {
      super();
      this.token = id;
      this.fname = fname;
      this.cgpa = cgpa;
   }
   public int getToken() {
      return token;
   }
   public String getFname() {
      return fname;
   }
   public double getCgpa() {
      return cgpa;
   }

   @Override
   public int compareTo(Student s) {
     if (this.getCgpa() > s.getCgpa()) return -1;
     else if (this.getCgpa() < s.getCgpa()) return 1;
     else {
       if (!this.getFname().equals(s.getFname())) return this.getFname().compareTo(s.getFname());
       else {
         return this.getToken() - s.getToken();
       }
     }
   }
}

public class Solution {

    public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      int totalEvents = Integer.parseInt(in.nextLine());
      PriorityQueue<Student> que = new PriorityQueue<>(totalEvents);
      while(totalEvents>0){
         String event = in.next();
         //Complete your code
         if (event.equals("ENTER")) {
             String fName = in.next();  double cgpa = in.nextDouble();  int token = in.nextInt();
             Student student = new Student(token, fName, cgpa);
             que.add(student);
         } else if (event.equals("SERVED")) {
             que.poll();
         }
          
         totalEvents--;
      }
      
      if (que.size() > 0) {
          while (!que.isEmpty()) {
            System.out.println(que.poll().getFname());
          }
      } else {
          System.out.println("EMPTY");
      }
    }
}
