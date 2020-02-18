import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
public class AccessingDataMongodbApplication{
  @Autowired
  private CustomerRepository customerRepository;
  private ToolRepository toolRepo;

  @GetMapping("/accessDBCustomer")
  public String getDB(Model model{
		findCustomerByName();
		return "accessDB"
  }

  @GetMapping("/accesDBTool")
  public String getToolDB(Model model){
	
  }

  @PostMapping("/accessDBTool")
  public void postDB(@ModelAttribute Tool tool){
		toolRepo.save(tool);
  }

  @PostMapping("/accessDBCustomer")
  public void postDB(@ModelAttribute Customer customer){
		customerRepository.save(customer);	
  }
}
