package max.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import max.model.Storage;
import max.servise.StorageService;

@RestController
@RequestMapping("/api/v1/storages")
public class StorageRestControllerV1 {
	
	private final StorageService storageService;
	
	public StorageRestControllerV1(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Storage> getStorages() {
		return storageService.getAllStorages();
	}
	
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> addStorage(@Valid @RequestBody Storage storage, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body("Введены некорректные данные");
		}
		storageService.addStorage(storage);
		return ResponseEntity.ok().body(storage);
	}
	
	@GetMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Long> deleteStorage(@PathVariable Long id) {
		storageService.deleteById(id);
		return ResponseEntity.ok().body(id);
	}
	
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> changeStorage(@Valid @RequestBody Storage storage, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body("Введены некорректные данные");
		}
	storageService.addStorage(storage);
	return ResponseEntity.ok().body(storage);
		
	}
 
}
