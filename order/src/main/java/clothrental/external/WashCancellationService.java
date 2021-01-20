
package clothrental.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="laundry", url="${api.laundry.url}")
public interface WashCancellationService {

    @RequestMapping(method= RequestMethod.POST, path="/washCancellations")
    public void cancelwash(@RequestBody WashCancellation WashCancellation);

}