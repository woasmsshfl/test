package site.metacoding.serverproject.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import site.metacoding.serverproject.domain.Download;
import site.metacoding.serverproject.domain.DownloadRepository;

@Controller
public class DownloadController {

    // DB에 INSERT하기 위한 의존성주입
    private DownloadRepository downloadRepository;

    public DownloadController(DownloadRepository downloadRepository) {
        this.downloadRepository = downloadRepository;
    }

    @CrossOrigin
    @GetMapping("/")
    public String download(Model model) {
        // 1. URI로 다운로드
        // Entity에 담을 Template 생성
        RestTemplate rt = new RestTemplate();

        // Download를 Entity로 생성
        Download[] d = rt.getForObject(
                "http://3.38.254.72:5000/api/hospital?sidoCdNm=부산&sgguCdNm=부산사하구",
                Download[].class);

        // 2. DB에 saveAll() + model에 담기
        // List로 배열하기
        List<Download> lists = Arrays.asList(d);
        // saveall하여 Insert실행
        downloadRepository.saveAll(lists);
        // list에 insert된 데이터를 model에 담기
        model.addAttribute("download", lists);

        // 3. 리턴
        return "/list";
    }

    @GetMapping("/download")
    public String downloadList(Model model) {
        List<Download> downloadList = downloadRepository.findAll();

        model.addAttribute("list", downloadList);

        return "/download";
    }
}
