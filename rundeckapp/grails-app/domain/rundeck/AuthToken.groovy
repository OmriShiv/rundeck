/*
 * Copyright 2016 SimplifyOps, Inc. (http://simplifyops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rundeck
class AuthToken {
    String token
    String authRoles
    Date expiration
    static belongsTo = [user:User]

    static constraints = {
        token(nullable:false,unique:true)
        authRoles(nullable:false)
        user(nullable:false)
        expiration(nullable: true)
    }
    static mapping = {
        authRoles type: 'text'
    }

    Set<String> authRolesSet() {
        return parseAuthRoles(authRoles)
    }

    static String generateAuthRoles(Collection<String> roles) {
        new HashSet(roles.collect { it.trim() }.findAll { it }).join(',')
    }

    static Set<String> parseAuthRoles(String authRoles) {
        if (!authRoles) {
            return []
        }
        new HashSet(authRoles.split(' *, *').collect { it.trim() }.findAll { it } as List)
    }
}
