# Contributing to WildernessWeather

When contributing to this repository, please first discuss the change you wish to make via issue,
email, or any other method with the owners of this repository before making a change.

Please note we have a code of conduct, please follow it in all your interactions with the project.

## Contributing Process

1. Ensure you are working on a new local branch based off of the default branch `develop`.
2. Make your changes / contributions.
3. Update the README.md with details of changes to the codebase, this includes new environment
   variables, new dependencies, useful file locations and useful information about any new
   functionality.
4. Create a pull request, merging `yourBranch -> develop`, and assign at least 1 **collaborator** to review it.
4. You may merge the Pull Request in once you have been approved by at least 1 **collaborator**.

To streamline the approval process, ensure you are following our code style and naming-conventions,
outlined below.

## Code Style and Guidelines for Contributors

### Order import statements
The correct ordering of import statements is:
1. Android imports
2. Imports from third parties (`com`, `junit`, `net`, `org`)
3. `java` and `javax`

The easiest way to keep import statements under control, is to use Android Studios optimise imports shortcut (`ctrl` + `alt` + `O`)

### Use Android Studio's code layout style
The easiest way to do this is to use the shortcut `ctrl` + `alt` + `L` on Windows, and `command` + `alt` + `L` on Mac.

### Be consistent

If you're editing code, take a few minutes to look at the surrounding code and determine its style

### Write short methods

When feasible, keep methods small and focused. We recognize that long methods are sometimes
appropriate, so no hard limit is placed on method length. If a method exceeds 40 lines or so, think
about whether it can be broken up without harming the structure of the program.

### Define fields in standard places
Define fields either at the top of the file or immediately before the methods that use them.

### Use readable and logical variable and method names
- Use snakeCase
- Method names should have descriptive signatures (title and arguments)
- Variables should be descriptive and express their purpose
- XML layout file elements should be prefixed with their type and an underscore. Eg a submit button should be called `btn_submit`, a textview displaying a total should be called `tv_total` and a list view showing previous transactions should be called `lv_previousTransactions`

### Use descriptive comments where needed
You should try and write self-describing code, however where needed, use comments to further explain what each section does.

### Write appropriate tests
You should try and write unit tests for each new method you create. These tests should be placed in the `com.cp3407.wildernessweather (test)` package.

### Commit messages should be in the imperative mood
Don't write a git commit subject line that talks about what you did, or what you are doing. Instead, describe what was done.  

`Fixing the layout error //bad`  
`Fix the layout error //good`

A good rule of thumb is that a git commit message can be appended to the statement "If applied, this commit will ..."  

`If applied, this commit will Fixing the layout error // bad`  
`If applied, this commit will Fix the layout error // good`

## Code of Conduct

### Our Pledge

In the interest of fostering an open and welcoming environment, we as contributors and maintainers
pledge to making participation in our project and our community a harassment-free experience for
everyone, regardless of age, body size, disability, ethnicity, gender identity and expression, level
of experience, nationality, personal appearance, race, religion, or sexual identity and orientation.

### Our Standards

Examples of behavior that contributes to creating a positive environment include:

* Using welcoming and inclusive language
* Being respectful of differing viewpoints and experiences
* Gracefully accepting constructive criticism
* Focusing on what is best for the community
* Showing empathy towards other community members

Examples of unacceptable behavior by participants include:

* The use of sexualized language or imagery and unwelcome sexual attention or advances
* Trolling, insulting/derogatory comments, and personal or political attacks
* Public or private harassment
* Publishing others' private information, such as a physical or electronic address, without explicit
  permission
* Other conduct which could reasonably be considered inappropriate in a professional setting

### Our Responsibilities

Project maintainers are responsible for clarifying the standards of acceptable behavior and are
expected to take appropriate and fair corrective action in response to any instances of unacceptable
behavior.

Project maintainers have the right and responsibility to remove, edit, or reject comments, commits,
code, wiki edits, issues, and other contributions that are not aligned to this Code of Conduct, or
to ban temporarily or permanently any contributor for other behaviors that they deem inappropriate,
threatening, offensive, or harmful.

### Scope

This Code of Conduct applies both within project spaces and in public spaces when an individual is
representing the project or its community. Examples of representing a project or community include
using an official project e-mail address, posting via an official social media account, or acting as
an appointed representative at an online or offline event. Representation of a project may be
further defined and clarified by project maintainers.

### Enforcement

Instances of abusive, harassing, or otherwise unacceptable behavior may be reported by contacting
the project team here on GitHub. All complaints will be reviewed and investigated and
will result in a response that is deemed necessary and appropriate to the circumstances. The project
team is obligated to maintain confidentiality with regard to the reporter of an incident. Further
details of specific enforcement policies may be posted separately.

Project maintainers who do not follow or enforce the Code of Conduct in good faith may face
temporary or permanent repercussions as determined by other members of the project's leadership.

### Attribution

This Code of Conduct is adapted from the [Contributor Covenant][homepage], version 1.4, available
at [http://contributor-covenant.org/version/1/4][version]

[homepage]: http://contributor-covenant.org

[version]: http://contributor-covenant.org/version/1/4/
