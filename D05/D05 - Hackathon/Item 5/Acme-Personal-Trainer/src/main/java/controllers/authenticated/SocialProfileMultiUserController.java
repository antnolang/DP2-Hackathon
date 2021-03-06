
package controllers.authenticated;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialProfileService;
import controllers.AbstractController;
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping(value = "/socialProfile/administrator,auditor,customer,nutritionist,trainer")
public class SocialProfileMultiUserController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------
	public SocialProfileMultiUserController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int actorId) {
		ModelAndView result;
		Integer principalId;
		Collection<SocialProfile> socialProfiles;
		Boolean isAuthorized;

		try {
			socialProfiles = this.socialProfileService.findSocialProfilesByActor(actorId);

			principalId = this.actorService.findPrincipal().getId();

			// We check if actor principal is owner's social profiles
			isAuthorized = principalId != null && principalId == actorId;

			result = new ModelAndView("socialProfile/list");
			result.addObject("socialProfiles", socialProfiles);
			result.addObject("actorId", actorId);
			result.addObject("requestURI", "socialProfile/administrator,auditor,customer,nutritionist,trainer/list.do");
			result.addObject("isAuthorized", isAuthorized);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int socialProfileId) {
		ModelAndView result;
		SocialProfile socialProfile;

		try {
			socialProfile = this.socialProfileService.findOneDisplay(socialProfileId);

			result = new ModelAndView("socialProfile/display");

			result.addObject("socialProfile", socialProfile);
			result.addObject("actorId", socialProfile.getActor().getId());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();

		result = this.createEditModelAndView(socialProfile);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		ModelAndView result;
		SocialProfile socialProfile;

		try {
			socialProfile = this.socialProfileService.findOneToEdit(socialProfileId);

			result = this.createEditModelAndView(socialProfile);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;
		SocialProfile socialProfileRec;

		socialProfileRec = this.socialProfileService.reconstruct(socialProfile, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(socialProfile);
		else
			try {
				this.socialProfileService.save(socialProfileRec);
				result = new ModelAndView("redirect:/socialProfile/administrator,auditor,customer,nutritionist,trainer/list.do?actorId=" + socialProfileRec.getActor().getId());
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(socialProfileRec, "socialProfile.linkProfile.unique");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialProfileRec, "socialProfile.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;
		int actorId;
		SocialProfile social_profile;

		social_profile = this.socialProfileService.findOne(socialProfile.getId());
		actorId = social_profile.getActor().getId();
		try {
			this.socialProfileService.delete(social_profile);

			result = new ModelAndView("redirect:/socialProfile/administrator,auditor,customer,nutritionist,trainer/list.do?actorId=" + actorId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------
	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile) {
		ModelAndView result;

		result = this.createEditModelAndView(socialProfile, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String messageCode) {
		ModelAndView result;
		Actor principal;
		int actorId;

		principal = this.actorService.findPrincipal();
		actorId = principal.getId();

		result = new ModelAndView("socialProfile/edit");
		result.addObject("socialProfile", socialProfile);
		result.addObject("messageCode", messageCode);
		result.addObject("actorId", actorId);

		return result;
	}

}
