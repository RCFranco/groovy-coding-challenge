ruleset {
    description 'Standard CodeNarc Ruleset'

    ruleset('rulesets/basic.xml')
    ruleset('rulesets/exceptions.xml')
    ruleset('rulesets/imports.xml')
    ruleset('rulesets/unnecessary.xml')
    ruleset('rulesets/groovyism.xml')
}