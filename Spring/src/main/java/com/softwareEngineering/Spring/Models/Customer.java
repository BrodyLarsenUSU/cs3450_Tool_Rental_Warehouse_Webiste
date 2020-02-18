import org.springframework.data.annotation.Id;

public class Customer{
		@id 
		public String id;
		private String name;

		Customer(){

		}

		Customer(String n){
			name = n;
		}

}
