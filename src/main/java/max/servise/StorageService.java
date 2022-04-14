package max.servise;

import java.util.List;

import org.springframework.stereotype.Service;

import max.model.Storage;
import max.repository.StorageRepository;

@Service
public class StorageService {
	
	private final StorageRepository storageRepository;

	private StorageService(StorageRepository storageRepository) {
		this.storageRepository = storageRepository;
	}
	
	public List<Storage> getAllStorages() {
		return storageRepository.findAll();
	}
	
	public void addStorage(Storage storage) {
		storageRepository.save(storage);
	}
	
	public void deleteById(Long id) {
		storageRepository.deleteById(id);
	}
	
}
