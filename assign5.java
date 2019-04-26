package 알고리즘5주차;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class assign5 {
	static Node root;
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		read();
		while(true)
		{
			System.out.print("$ ");
			String message = scan.next();
			if (message.contentEquals("find")) {
				{String msg = scan.next();
				find(msg,root);}
			}
			else if (message.contentEquals("size")) {
				System.out.println(size(root));
			}
			else if (message.contentEquals("add")) {
				add();
			}
			else if (message.contentEquals("delete")) {
				{String msg = scan.next();
				if(delete(msg))
					System.out.println("Deleted successfully.");
				else
					System.out.println("Not found.");
				}
			}
			else if (message.contentEquals("deleteall")) {
				{String msg = scan.next();
				System.out.println(deleteall(msg)+" words were deleted successfully.");}
			}
			else if(message.contentEquals("exit"))
				break;
		}
	}
	public static void read() {
		try {
			Scanner infile = new Scanner(new File("shuffled_dict.txt"));//
			while(infile.hasNext()) {
				String buffer = infile.nextLine();
				if(!buffer.contentEquals("")) {
					Node node=new Node(buffer.substring(0, buffer.indexOf("(")-1),
							buffer.substring(buffer.indexOf("(")-1,buffer.indexOf(")")+1),
							buffer.substring(buffer.indexOf(")")+1));
					insert(node);
				}
			}
			infile.close();
		}
		catch(FileNotFoundException e) {System.out.println("No file");
		System.exit(0);}
	}
	static int size(Node node) {
		if(node==null) return 0;
		return 1+size(node.left)+size(node.right);
	}
	static void find(String word,Node node) {
		if(node==null)
			return;
		else if(word.contentEquals(node.w)) {
			System.out.println(node.m);
			return;
		}
		else {
			if(node.w.compareTo(word)>0)node=node.left;
			else node=node.right;
			find(word,node);
		}
	}
	static void add() {
		System.out.print("word : ");
		String a=scan.next();
		scan.nextLine();
		System.out.print("class : ");
		String b=scan.nextLine();
		System.out.print("meaning : ");
		String c=scan.nextLine();
		Node node=new Node(a,b,c);
		insert(node);
	}
	static boolean delete(String str) {
		Node delete=root;
		Node parent=root;
		boolean isleft=true;
		while(!delete.w.equals(str)) {
			parent=delete;
			if(delete.w.compareTo(str)>0) 
			{delete=delete.left;
			isleft=true;
			}
			else if(delete.w.compareTo(str)<0) 
			{delete=delete.right;
			isleft=false;
			}
			if(delete==null)
				return false;
		}
		Node replace;
		if(delete.right==null&&delete.left==null)
		{
			if(delete==root)
				root=null;
			else if(isleft)
				parent.left=null;
			else if(!isleft)
				parent.right=null;
		}
		else if(delete.left==null) {
			replace=delete.right;
			if(delete==root)
				root=replace;
			else if(isleft)
				parent.left=replace;
			else if(!isleft)
				parent.right=replace;
		}
		else if(delete.right==null) {
			replace=delete.left;
			if(delete==root)
				root=replace;
			else if(isleft)
				parent.left=replace;
			else if(!isleft)
				parent.right=replace;
		}
		else {
			Node rightsubtree=delete.right;
			replace=getSuccessor(delete.right);	
			if(delete==root)
				root=replace;
			else if(isleft)
				parent.left=replace;
			else if(!isleft)
			parent.right=replace;
			if(replace!=rightsubtree)
			replace.right=rightsubtree;
			replace.left=delete.left;
		}
		return true;
	}
	static Node getSuccessor(Node node) {
		Node parent=node;
		Node successor=node;
		while(successor.left!=null) {
			parent=successor;
			successor=successor.left;}
		if(parent==successor)
			return successor;
		parent.left=successor.right;
		successor.right=null;
		return successor;
	}
	static int deleteall(String str) {
		int count=0;
		try {
			Scanner infile = new Scanner(new File(str));
			while(infile.hasNext()) {
				String buffer = infile.next();
				if(delete(buffer))
					count+=1;
			}
			infile.close();
		}
		catch(FileNotFoundException e) {System.out.println("No file");
		System.exit(0);}
		return count;
	}
	static Node insert(Node node) {
		Node t=root;
		Node p=null;
		while(t!=null) {
			if(t.w==node.w) return t;
			p=t;
			if(p.w.compareTo(node.w)>0)t=p.left;
			else t=p.right;
		}
		if(p!=null) {
			if(p.w.compareTo(node.w)>0)p.left=node;
			else p.right=node;
		}
		else root=node;
		return root;
	}
}
class Node{
	Node left,right;
	String w,c,m;
	Node(String a,String b, String c){
		this.w=a;
		this.c=b;
		this.m=c;
	}
}