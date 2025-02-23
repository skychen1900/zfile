package im.zhaojun.zfile.controller.install;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.crypto.SecureUtil;
import im.zhaojun.zfile.model.dto.SystemConfigDTO;
import im.zhaojun.zfile.model.support.ResultBean;
import im.zhaojun.zfile.service.SystemConfigService;

/**
 * 系统安装初始化
 * 
 * @author zhaojun
 */
@RestController
public class InstallController {

	@Resource
	private SystemConfigService systemConfigService;

	@GetMapping("/is-installed")
	public ResultBean isInstall() {
		if (StringUtils.hasText(systemConfigService.getAdminUsername())) {
			return ResultBean.error("请勿重复初始化");
		}
		return ResultBean.success();
	}

	@PostMapping("/doInstall")
	public ResultBean install(SystemConfigDTO systemConfigDTO) {
		if (StringUtils.hasText(systemConfigService.getAdminUsername())) {
			return ResultBean.error("请勿重复初始化.");
		}

		systemConfigDTO.setPassword(SecureUtil.md5(systemConfigDTO.getPassword()));
		systemConfigService.updateSystemConfig(systemConfigDTO);

		return ResultBean.success();
	}

}