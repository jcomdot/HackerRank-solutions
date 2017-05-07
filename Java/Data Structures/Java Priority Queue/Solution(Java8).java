import java.util.*;

class Student {
	private int token;
	private String fname;
	private double cgpa;

	public Student(int id, String fname, double cgpa) {
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
}

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int totalEvents = Integer.parseInt(in.nextLine());
		Queue<Student> que = new PriorityQueue<>(totalEvents,
				Comparator.comparing(Student::getCgpa).reversed().thenComparing(Student::getFname).thenComparing(Student::getToken));
		while (totalEvents > 0) {
			String event = in.next();
			// Complete your code
			if (event.equals("ENTER")) {
				String fName = in.next();
				double cgpa = in.nextDouble();
				int token = in.nextInt();
				Student student = new Student(token, fName, cgpa);
				que.add(student);
			} else if (event.equals("SERVED")) {
				que.poll();
			}

			totalEvents--;
		}
		in.close();
		

		if (que.size() > 0) {
			while (!que.isEmpty()) {
				System.out.println(que.poll().getFname());
			}
		} else {
			System.out.println("EMPTY");
		}
	}
}
