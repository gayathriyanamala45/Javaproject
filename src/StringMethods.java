
public class StringMethods {

	public static void main(String[] args) {
		String b="Hii Hello Java";
		System.out.println(b.length());
		
		System.out.println(b.charAt(2));
		System.out.println(b.toUpperCase());
		System.out.println(b.toLowerCase());
		System.out.println(b.equals("hii hello Java"));
		System.out.println(b.equalsIgnoreCase("hii hello java"));
		System.out.println(b.concat("programming"));
		System.out.println(b.contains("programming"));
		System.out.println(b.startsWith("hello"));
		System.out.println(b.endsWith("Java"));
		System.out.println(b.substring(3,6));
		System.out.println(b.indexOf("java"));
		System.out.println(b.replace("Java","java"));
		System.out.println(b.trim());
		System.out.println(b.replace("java","Python P"));


	}

}
