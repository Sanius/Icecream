import { isLoggedIn } from '@/lib/utils'
import { requestGetCustomerProfile } from '@/repositories/frontier/customers'
import { auth, signOut } from '@/repositories/identity'
import type { CustomerExtended, Session } from '@/types'
import UnauthorizedPage from '@/app/unauthorized'
import { PersonalProfileCard } from './_components/personal-profile-card'
import { AddressCard } from './_components/address-card'
import { SideBar } from './_components/sidebar'
import { UnauthorizedException } from '@/exceptions/api-request'

const Page = async () => {
  const session = await auth()
  if (!isLoggedIn(session)) {
    return <UnauthorizedPage />
  }
  const customerInfoResponse = await requestGetCustomerProfile(session as unknown as Session)

  return (
    <div className="container grid grid-cols-12 items-start gap-6 pt-4 pb-16">
      <SideBar customerInfo={customerInfoResponse} />
      <div className="col-span-9 grid grid-cols-3 gap-4">
        <PersonalProfileCard customerInfo={customerInfoResponse} />
        <AddressCard title="Shipping address" address={{}} />
        <AddressCard title="Blling address" address={{}} />
      </div>
    </div>
  )
}

export default Page
